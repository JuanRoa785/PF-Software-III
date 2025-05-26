import { Component, Input, TemplateRef} from '@angular/core';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AppService } from '../../app.service';
import { Router } from '@angular/router';
import { ModalVerifComponent } from '../../components/modal-verif/modal-verif.component';
import { TokenService } from '../../token.service';


@Component({
  selector: 'app-card-libro',
  standalone: true,
  imports: [],
  templateUrl: './card-libro.component.html',
  styleUrl: './card-libro.component.css'
})
export class CardLibroComponent {
  constructor(
    private appService: AppService,
    private router:Router,
    private modalService:NgbModal,
    private tokenService:TokenService
  ){};

  @Input() libro!: { 
    id: number;
    generoLiterario: {
      id: number;
      nombre: string;
      portada: string;
      descripcion: string;
    };
    nombre: string;
    sinopsis: string;
    id_portada: string;
    portada: string;
    precio: number;
    impuesto: number;
    stock: number;
    autor: string;
    editorial: string;
    edicion: string;
    fecha_publicacion: string;
    idioma: string;
    numero_paginas: number;
    dimensiones: string;
    coleccion: string
  };

  isModalUp:boolean = false;
  modalRef?: NgbModalRef;

  openInfoModal(content: TemplateRef<any>) {
		this.modalRef = this.modalService.open(content, { size: 'xl', centered:true });
    this.isModalUp = true;
	}

  closeInfoModal() {
		if (this.modalRef) {
      this.modalRef.close();
      this.modalRef = undefined
    }
    this.cantidadComprar = 1;
    this.isModalUp = false;
	}

  getStock(): {nombre: string, color: string} {
    if (this.libro != null) {
      if (this.libro.stock >= 10) {
        return {nombre: 'Disponible', color:'#28a745'};
      }
      else if (this.libro.stock > 0 && this.libro.stock < 10) {
        return {nombre: 'Ultimas Unidades', color:'#ffc107'};
      }
      else {
        return {nombre: 'Agotado', color:'#dc3545'};
      }
    }
    else {
      return {nombre: 'Libro Nulo', color: '#6c757d'};
    }
  }

  cantidadComprar:number = 1;

  aumentarCantidad(): void {
    this.cantidadComprar = this.cantidadComprar + 1; 
  }

  disminuirCantidad(): void {
    if (this.cantidadComprar > 1) {
      this.cantidadComprar = this.cantidadComprar - 1;
    }
  }

  getTotalPrice(precio:number, impuesto:number): string{
    let total = Math.round(precio * (1 + impuesto));
    return(total.toLocaleString('es-CO'))
  }

  formatearSinopsis(sinopsis:string): string {
    return sinopsis.replace(/\\n\\n/g, '<br><br>')
  }

  redirigir(destino: string) {
    this.appService.agregarLibroCarrito(this.libro.id, this.cantidadComprar).subscribe(
      (response: any) => {
        // Solo si todo salió bien
        if (destino === 'checkout') {
          this.router.navigate(['/checkout']);
        } else {
          this.router.navigate(['cliente/carrito']);
        }
        this.modalService.dismissAll(); // Cerramos los modales
      },
      (error: any) => {
        console.error('Error añadiendo un producto al carrito:', error);
      }
    );
  }


  iniciarCompra(destino: string) {
    this.tokenService.isAuthenticated().subscribe(
      (isAuth) => {
        const modalRef = this.modalService.open(
          ModalVerifComponent, {
          backdrop: 'static',
          centered: true,
        });

        if (!isAuth) {
          const infoModal = {
            titulo: '¿Ya iniciaste sesión?',
            mensaje: `Parece que no has iniciado sesión, hazlo e intenta nuevamente`,
            notificacion: true
          };

          modalRef.componentInstance.modal = infoModal;
          modalRef.componentInstance.tareaARealizar = () => this.router.navigate(['/login']);;
        } else {

          const infoModal = {
            titulo: '¿Esta seguro?',
            mensaje: `¿De verdad quiere agregar al carrito el siguiente libro? <br> <br> Titulo: ${this.libro.nombre} <br> <br> Cantidad: ${this.cantidadComprar}`,
            notificacion: false
          };

          if (destino == 'checkout') {
            infoModal.mensaje = `¿De verdad quiere comprar el siguiente libro? <br> <br> Titulo: ${this.libro.nombre} <br> <br>  Cantidad: ${this.cantidadComprar}`
          }

          modalRef.componentInstance.modal = infoModal;
          modalRef.componentInstance.tareaARealizar = () => this.redirigir(destino);
        }
      }
    )
  }
}