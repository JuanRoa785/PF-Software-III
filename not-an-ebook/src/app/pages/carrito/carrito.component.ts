import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AppService } from '../../app.service';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ModalVerifComponent } from '../../components/modal-verif/modal-verif.component';

@Component({
  selector: 'app-carrito',
  standalone: true,
  imports: [
    FormsModule, 
    CommonModule
  ],
  templateUrl: './carrito.component.html',
  styleUrl: './carrito.component.css'
})
export class CarritoComponent {
  constructor(
    private appService: AppService,
    private router: Router,
    private modalService:NgbModal
  ) { };

  carrito:any[] = [];
  detalleCarrito: any[] = [];
  isLoading: boolean = true;

  ngOnInit(): void {
    this.getCarrito();
    window.addEventListener('beforeunload', this.onBeforeUnload.bind(this));
  }

  getCarrito(): void { 
    this.appService.getCarrito().subscribe(
      (response:any) => {
        this.carrito = response;
        this.detalleCarrito = response.detalleCarrito;
        
        //Si sucedió un error al finalizar la compra y redirigió al usuario
        //al carrito, automaticamente actualizara la cantidad a comprar para que
        //iguale el stock disponible
        this.detalleCarrito.forEach(detalle => {
          if (detalle.cantidad > detalle.libro.stock) {
            detalle.cantidad = detalle.libro.stock
          }
        });

        this.getSubtotal();

        this.isLoading = false;
        //console.log(this.carrito);
      },
      (error) => {
        console.error('Error obteniendo el carrito compras', error);
        this.isLoading = false;
      }
    );
  }

  getPrecioImpuesto(libro: any): string {
    const getPrecioImpuesto = libro.precio * (1+libro.impuesto); 
    return getPrecioImpuesto.toLocaleString('es-CO', 
      { minimumFractionDigits: 0, maximumFractionDigits: 0 });
  }

  getImpuesto(product: any): number {
    return Math.round(product.impuesto * 100);
  }

  getSubTotalLibro(detalle:any): string {
    var subtotal = detalle.cantidad * ((detalle.libro.precio) * (1 + detalle.libro.impuesto))

    return subtotal.toLocaleString('es-CO', { minimumFractionDigits: 0, maximumFractionDigits: 0 });
  }

  eliminarDetalle(idDetalle:number){
    this.appService.eliminarDetalleCarrito(idDetalle).subscribe(
      (response) => {
        this.getCarrito();
        //console.log(this.carrito);
      },
      (error) => {
        console.error("Error Deleting a 'DetalleProducto'", error);
      }
    );
  }

  goCheckout(){
    if (this.detalleCarrito.length == 0) {
      const modalRef = this.modalService.open(
            ModalVerifComponent, {
            backdrop: 'static',
            centered: true,
          }
          );
      
          const infoModal = {
            titulo: '¡No tan rapido!',
            mensaje: `¡Tu carrito de compras esta vacio, por favor añade un libro!`,
            notificacion: true
          };
      
          modalRef.componentInstance.modal = infoModal;
          modalRef.componentInstance.tareaARealizar = () => this.router.navigate(['/buscarLibro'], { queryParams: { titulo: "", genero: "" } });
    }else {
      window.location.href = '/checkout';;
    }
  }

  actualizarDetalleCarrito(detalle:any){
    this.appService.actualizarDetalleCarrito(detalle.id, detalle.cantidad).subscribe(
      (response) => {
        //console.log(response);
        this.getCarrito();
      },
      (error) => {
        console.error("Error actualizando el detalle carrito:", error);
      }
    );
  }

  aumentarCantidad(detalle: any): void {
    if (detalle.cantidad < detalle.libro.stock) {
      detalle.cantidad = detalle.cantidad + 1; 
    }else {
      const modalRef = this.modalService.open(
        ModalVerifComponent, {
        backdrop: 'static',
        centered: true,
      }
      );

      const infoModal = {
        titulo: 'Stock insuficiente',
        mensaje: `Stock of '${detalle.libro.nombre}' = ${detalle.libro.stock} 
        <br> <br> Por favor reduzca el número de libros que desea comprar.`
      };

      modalRef.componentInstance.modal = infoModal;
      modalRef.componentInstance.tareaARealizar = () => modalRef.close();
    }
    this.getSubtotal();
    //this.actualizarDetalleCarrito(detalle)*/
  }

  disminuirCantidad(detalle: any): void {
    if (detalle.cantidad > 1) {
      detalle.cantidad = detalle.cantidad - 1; 
    }
    this.getSubtotal();
    //this.actualizarDetalleCarrito(detalle)*/
  }

  subTotal:string = '';

  getSubtotal() {
    var subTotal:number = 0;
    this.detalleCarrito.forEach(detalle => {
      subTotal = subTotal + (detalle.cantidad * ((detalle.libro.precio) * (1 + detalle.libro.impuesto)))
      if (detalle.cantidad > detalle.libro.stock) {
        detalle.cantidad = detalle.libro.stock
      }
    });
    this.subTotal = Math.round(subTotal).toLocaleString();
    //console.log(this.subtotal)
  }

  ngOnDestroy(): void {
    this.detalleCarrito.forEach(detalle => {
        this.actualizarDetalleCarrito(detalle);
    });
    window.removeEventListener('beforeunload', this.onBeforeUnload.bind(this));
  }

  onBeforeUnload(event: BeforeUnloadEvent) {
    this.detalleCarrito.forEach(detalle => {
        this.actualizarDetalleCarrito(detalle);
    });
  }

}
