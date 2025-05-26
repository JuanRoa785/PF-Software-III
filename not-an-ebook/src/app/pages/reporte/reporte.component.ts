import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AppService } from '../../app.service';
import { CommonModule } from '@angular/common';
import { NgbPagination, NgbAlertModule, NgbDatepickerModule, NgbCalendar, NgbDateAdapter
} from '@ng-bootstrap/ng-bootstrap';
import { DetalleOrdenComponent } from '../detalle-orden/detalle-orden.component';

@Component({
  selector: 'app-reporte',
  standalone: true,
  imports: [
    FormsModule, CommonModule, NgbPagination, NgbDatepickerModule, 
    NgbAlertModule, FormsModule, DetalleOrdenComponent
  ],
  templateUrl: './reporte.component.html',
  styleUrl: './reporte.component.css'
})
export class ReporteComponent implements OnInit{
  constructor(
    private appService: AppService, 
    private ngbCalendar: NgbCalendar,
		private dateAdapter: NgbDateAdapter<string>
  ) {};

  ventas: any[] = [];
  nombreCliente: string = '';
  sortTotalDesc: boolean = true;

  currentPage: number = 1;
  itemsPerPage: number = 10;
  
  earliestDate: any = {};
  latestDate: any = {};

  viewDetail = false; 
  selectedSale: any;

  isLoading: boolean = true;

  ngOnInit(): void {
    this.earliestDate = this.getToday();
    this.latestDate = this.getToday();
    this.getSales();

    this.updatePaginationSize();
    window.addEventListener('resize', this.updatePaginationSize.bind(this));
  }

  getToday() {
		return this.dateAdapter.toModel(this.ngbCalendar.getToday())!;
	}

  getSales() {
    //Reiniciar el arreglo que contiene las ventas para solo mostar el loading
    this.ventas = []
    this.isLoading = true;

    //Auxiliares para pasar las fechas en los rangos correctos
    const fechaInf = `${this.earliestDate.year}-${this.earliestDate.month}-${this.earliestDate.day}`
    const fechaSup = `${this.latestDate.year}-${this.latestDate.month}-${this.latestDate.day}`
    
    this.appService.getVentas(
      this.nombreCliente, fechaInf, fechaSup, this.sortTotalDesc).subscribe(
      (response) => {
        this.ventas = response;
        this.isLoading = false;
        //console.log(response);
      },
      (error) => {
        this.isLoading = false;
        console.error('Error Cargando las ventas', error);
      }
    );
  }

  convertObjectToDate(object: any){
    return new Date(object.year, object.month - 1, object.day);
  }

  onDateChange(type: string, event: any) {
    if (type === 'earliest') {
      //Verificamos que la nueva fecha no sea despues de hoy
      if (this.convertObjectToDate(this.earliestDate) > this.convertObjectToDate(this.getToday())) {
        alert("¡De momento no hay ventas en el futuro!");

        if (this.convertObjectToDate(this.getToday()) > this.convertObjectToDate(this.latestDate)) {
          //Se igualan las fechas
          this.earliestDate = this.latestDate;
        }else {
          this.earliestDate = this.getToday();
        }

        //Como cambio la fecha actualizamos las ventas
        this.getSales();
        return;
      } 
      
      //Verificamos que la nueva fecha no sea despues del limite superior del rango
      if (this.convertObjectToDate(this.earliestDate) > this.convertObjectToDate(this.latestDate)) {
        alert("¡Por favor seleccione un rango de fechas valido!")
        
        if (this.convertObjectToDate(this.getToday()) > this.convertObjectToDate(this.latestDate)) {
          //Se igualan las fechas
          this.earliestDate = this.latestDate;
        }else {
          this.earliestDate = this.getToday();
        }
        
        //Como cambio la fecha actualizamos las ventas
        this.getSales();
        return;
      }

      //Si las fechas son validas actualice las ventas:
      this.getSales();

      //console.log('Earliest Date changed:', event);

    } else if (type === 'latest') {
      //Verificamos que la nueva fecha no sea despues de hoy
      if (this.convertObjectToDate(this.latestDate) > this.convertObjectToDate(this.getToday())) {
        alert("¡De momento no hay ventas en el futuro!");
        this.latestDate = this.getToday();

        //Como cambio la fecha actualizamos las ventas
        this.getSales();
        return;
      }

      //Verificamos que la nueva fecha no sea despues del limite superior del rango
      if (this.convertObjectToDate(this.earliestDate) > this.convertObjectToDate(this.latestDate)) {
        alert("¡Por favor seleccione un rango de fechas valido!")
        this.latestDate = this.getToday();

        //Como cambio la fecha actualizamos las ventas
        this.getSales();
        return;
      }

      //Si las fechas son validas actualice las ventas:
      this.getSales();

      //console.log('Latest Date changed:', event);
    }
  }

  formatDate(dateString: string): string {
    const date = new Date(dateString);
    
    // Extraer componentes de fecha y hora
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0'); // Mes comienza en 0
    const day = String(date.getDate()).padStart(2, '0');
    const hours = String(date.getHours()).padStart(2, '0');
    const minutes = String(date.getMinutes()).padStart(2, '0');
    
    // Formato deseado: YYYY/MM/DD HH:MM
    return `${year}/${month}/${day} ${hours}:${minutes}`;
  }

  formatTotal(venta: any): string {
    const total = Math.round(venta.total);
    return total.toLocaleString('es-CO', { minimumFractionDigits: 0, maximumFractionDigits: 0 });
  }

  onKeydown(event: KeyboardEvent): void {
    if (event.key === 'Enter') {
      this.getSales();
    }
  }

  get paginatedSales() {
    const startIndex = (this.currentPage - 1) * this.itemsPerPage;
    return this.ventas.slice(startIndex, startIndex + this.itemsPerPage);
  }

  onPageChange(page: number): void {
    this.currentPage = page;

    requestAnimationFrame(() => {
      const activeElement = document.activeElement as HTMLElement;
      if (activeElement && activeElement.blur) {
        activeElement.blur();
      }
    });
  }

  showSaleDetail(sale: any) {
    this.selectedSale = sale;
    this.viewDetail = true;
  }

  paginationMaxSize: number = 8;

  updatePaginationSize(): void {
    const width = window.innerWidth;

    if (width < 500) {
      this.paginationMaxSize = 4;
    } else if (width < 768) {
      this.paginationMaxSize = 5;
    } else {
      this.paginationMaxSize = 8;
    }
  }

}
