import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AppService } from '../../app.service';
import { CommonModule } from '@angular/common';
import { NgbPagination } from '@ng-bootstrap/ng-bootstrap';
import { Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ModalVerifComponent } from '../../components/modal-verif/modal-verif.component';

@Component({
  selector: 'app-inventario',
  standalone: true,
  imports: [
    FormsModule, 
    CommonModule, 
    NgbPagination
  ],
  templateUrl: './inventario.component.html',
  styleUrl: './inventario.component.css'
})
export class InventarioComponent implements OnInit {
  constructor(
    private appService: AppService,
    private router:Router,
    private modalService:NgbModal
  ) {};

  ngOnInit(): void {
    this.getLibrosFiltrados();
    this.getGenerosLiterarios(); 
  }

  generos:any[] = [];
  libros:any[] = [];
  
  currentPage: number = 1;
  itemsPerPage: number = 10;
  
  tituloLibro:string = '';

  isLoading: boolean = false;
  generoSelect = '';

  getGenerosLiterarios(): void {
    this.appService.getGenerosLiterarios().subscribe(
      (response) => {
        this.generos = response;
      },
      (error) => {
        console.error('Error cargando los generos Literarios', error);
      }
    );
  }

  getLibrosFiltrados(): void {
    this.isLoading = true;
    this.appService.getLibrosFlitrados(this.tituloLibro, this.generoSelect).subscribe(
      (response) => {
        this.libros = response.sort((a: any, b: any) => a.id - b.id); //Ordena de mas reciente a mas viejo;
        this.isLoading = false;
        //console.log(response);
      },
      (error) => {
        console.error('Error cargando la dirección del usuario:', error);
      }
    );
  }

  get paginatedBooks() {
    const startIndex = (this.currentPage - 1) * this.itemsPerPage;
    return this.libros.slice(startIndex, startIndex + this.itemsPerPage);
  }

  onPageChange(page: number): void {
    this.currentPage = page;
  }

  onKeydown(event: KeyboardEvent): void {
    if (event.key === 'Enter') {
      this.getLibrosFiltrados();
    }
  }

  getTotalPrice(precio:number, impuesto:number): string{
    let total = Math.round(precio * (1 + impuesto));
    return(total.toLocaleString('es-CO'))
  }
 
  editarLibros(idLibro:number) {
    this.router.navigate(['admin/crudLibro'], { queryParams: { idLibro: idLibro } });
  }

  eliminarLibro(idLibro:number) {
    this.appService.eliminarLibro(idLibro).subscribe(
      (response) => {
        
        //Pseudo recargamos la pagina
        this.router.navigate(['admin/inventario']);
        this.tituloLibro = '';
        this.generoSelect = '';
        this.getLibrosFiltrados();

        //console.log(response);
      },
      (error) => {
        console.error('Error eliminando el libro:', error);
      }
    );
  }

  modalEliminarLibro(idLibro:number) {
    const modalRef = this.modalService.open(
      ModalVerifComponent, {
      backdrop: 'static',
      centered: true,
    }
    );

    const infoModal = {
      titulo: '¿Esta seguro?',
      mensaje: `¿De verdad quiere eliminar el libro con ID = ${idLibro}? <br> <br> ¡Esta información no podrá ser recuperada!`,
      notificacion: false
    };

    modalRef.componentInstance.modal = infoModal;
    modalRef.componentInstance.tareaARealizar = () => this.eliminarLibro(idLibro);
  }
}
