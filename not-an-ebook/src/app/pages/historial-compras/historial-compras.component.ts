import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AppService } from '../../app.service';
import { CommonModule } from '@angular/common';
import { NgbPagination } from '@ng-bootstrap/ng-bootstrap';
import { DetalleOrdenComponent } from '../detalle-orden/detalle-orden.component';

@Component({
  selector: 'app-historial-compras',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    NgbPagination,
    DetalleOrdenComponent
  ],
  templateUrl: './historial-compras.component.html',
  styleUrl: './historial-compras.component.css'
})
export class HistorialComprasComponent implements OnInit{
  constructor(
    private appService: AppService
  ) {};

  ngOnInit() {
    this.getOrders();
  }

  ordenes: any[] = [];
  tituloLibro:string = '';
  sortAsc:boolean = false;

  currentPage: number = 1;
  itemsPerPage: number = 10;

  viewDetail = false; 
  selectedOrder: any = {};
  isLoading: boolean = true;

  getOrders() {
    this.isLoading = true;

    //Se vacian para que se muestre el Loading... cada vez que se actualicen 
    //los parametros de los filtros
    this.ordenes = []; 

    this.appService.getOrdenesUsuario(this.tituloLibro, this.sortAsc).subscribe(
      (response: any) => {
        this.ordenes = response;
        this.sortOrdenes();
        this.isLoading = false;
        //console.log(response);
      },
      (error) => {
        console.error('Error Cargando las ordenes del usuario', error);
        this.isLoading = false;
      }
    );
  }

  sortOrdenes() {
    if (this.sortAsc) {
      //De la orden mas vieja a la mas nueva
      this.ordenes = this.ordenes.sort((a: any, b: any) => 
        new Date(a.fecha).getTime() - new Date(b.fecha).getTime());
    } else {
      //De la orden mas nueva a la mas vieja
      this.ordenes = this.ordenes.sort((a: any, b: any) => 
        new Date(b.fecha).getTime() - new Date(a.fecha).getTime());
    }
  }

  formatTotal(total:number):string {
    return Math.round(total).toLocaleString();
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

  onKeydown(event: KeyboardEvent): void {
    if (event.key === 'Enter') {
      this.getOrders()
    }
  }

  get paginatedOrders() {
    const startIndex = (this.currentPage - 1) * this.itemsPerPage;
    return this.ordenes.slice(startIndex, startIndex + this.itemsPerPage);
  }

  onPageChange(page: number): void {
    this.currentPage = page;
  }

  showOrderDetail(order: any) {
    this.selectedOrder = order;
    console.log(this.selectedOrder); 
    this.viewDetail = true;
  }
  
}
