import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { TokenService } from '../../token.service';
import { AppService } from '../../app.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ModalDireccionComponent } from '../../components/modal-direccion/modal-direccion.component';
import { ModalVerifComponent } from '../../components/modal-verif/modal-verif.component';

@Component({
  selector: 'app-checkout',
  standalone: true,
  imports: [ 
    CommonModule, 
    FormsModule
  ],
  templateUrl: './checkout.component.html',
  styleUrl: './checkout.component.css'
})
export class CheckoutComponent {
  constructor(
    private tokenService: TokenService,
    private router: Router,
    private modalService: NgbModal,
    private appService: AppService
  ) {
    this.tokenService.isAuthenticated().subscribe(
      (isAuth) => {
        if (!isAuth) {
          const modalRef = this.modalService.open(
            ModalVerifComponent, {
            backdrop: 'static',
            centered: true,
          });

          const infoModal = {
            titulo: '¿Ya iniciaste sesión?',
            mensaje: `Parece que no has iniciado sesión, hazlo e intenta nuevamente`,
            notificacion: true
          };

          modalRef.componentInstance.modal = infoModal;
          modalRef.componentInstance.tareaARealizar = () => this.router.navigate(['/login']);
        } 
      }
    );
  }

  ngOnInit(): void {
    this.getUserData();
    this.getCarrito();
  }

  user: any = {
    "id": 0,
    "nombres": "N.A",
    "apellidos": "",
    "email": "N.A",
    "telefono": "N.A",
    "cuenta_activa": true,
    "tipoUsuario": {
      "id": 0,
      "nombre": "string",
      "descripcion": "string"
    }
  }

  direccion: any = {
    "id": 0,
    "pais": "N.A",
    "region": "N.A",
    "ciudad": "N.A",
    "codigo_postal": "N.A",
    "direccion": "N.A",
    "idUsuario": 0,
    "nombreUsuario": "N.A"
  };

  getUserData(){
    this.tokenService.getUser().subscribe(
      (response) => {
        this.user = response;
        this.direccion.idUsuario = this.user.id
        this.getDireccion();
      },
      (error) => {
        console.error('Error obteniendo la info del usuario:', error);
      }
    );
  }

  getDireccion(){
    this.appService.getDireccion(this.user.id).subscribe(
      (response: any[]) => {
        this.direccion = response[0];
        //console.log(response[0]);
      },
      (error) => {
        console.error('Error obteniendo la dirección del usuario:', error);
      }
    );
  }

  openModalDireccion() {
    const modalRef = this.modalService.open(
      ModalDireccionComponent, {
      backdrop: 'static',
      centered: true,
    }
    );

    //Generamos una copia de this.direccion para que sean "independientes"
    modalRef.componentInstance.address = JSON.parse(JSON.stringify(this.direccion));

    modalRef.closed.subscribe(() => {
      this.getDireccion();
    });
  }

  carrito:any[] = [];
  detalleCarrito: any[] = [];

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
        //console.log(this.carrito);
      },
      (error) => {
        console.error('Error obteniendo el carrito compras', error);
      }
    );
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

  getPrecioImpuesto(libro: any): string {
    const getPrecioImpuesto = libro.precio * (1+libro.impuesto); 
    return getPrecioImpuesto.toLocaleString('es-CO', 
      { minimumFractionDigits: 0, maximumFractionDigits: 0 });
  }

  direccionValida():boolean {
    if (this.direccion.pais == '' || this.direccion.pais == 'N.A') {
      return false;
    }

    if (this.direccion.region == '' || this.direccion.region == 'N.A') {
      return false;
    }

    if (this.direccion.ciudad == '' || this.direccion.ciudad == 'N.A') {
      return false;
    }

    if (this.direccion.codigo_postal == '' || this.direccion.codigo_postal == 'N.A') {
      return false;
    }

    if (this.direccion.direccion == '' || this.direccion.direccion == 'N.A') {
      return false;
    }

    return true;
  }

  registrarVenta() {
    this.appService.registrarVenta(this.user.email, '').subscribe(
      (response) => {
        const modalRef = this.modalService.open(
          ModalVerifComponent, {
          backdrop: 'static',
          centered: true,
        });

        const infoModal = {
          titulo: '¡Compra Exitosa!',
          mensaje: `¡Tu pedido ha sido registrado!<br> Puedes seguir su progreso desde tu historial de compras.`,
          notificacion: true
        };

        modalRef.componentInstance.modal = infoModal;
        modalRef.componentInstance.tareaARealizar = () => this.router.navigate(['/cliente/historialCompras']);

        //console.log(response);
      },
      (error) => {
        console.error('Error registrando la venta:', error);
        const modalRef = this.modalService.open(
          ModalVerifComponent, {
          backdrop: 'static',
          centered: true,
        });

        const infoModal = {
          titulo: '¡Ha sucedido un error!',
          mensaje: `Es posible que alguno de los libros seleccionados ya no esté disponible.<br><br>
          ¿Quieres ir al carrito y actualizar tu selección antes de intentarlo de nuevo?`,
          notificacion: true
        };

        modalRef.componentInstance.modal = infoModal;
        modalRef.componentInstance.tareaARealizar = () => this.router.navigate(['/cliente/carrito']);
      }
    );
  }

  openVerifModal() {
    const modalRef = this.modalService.open(
      ModalVerifComponent, {
      backdrop: 'static',
      centered: true,
    });

    if (!this.direccionValida()) {
      const infoModal = {
        titulo: 'Dirección invalida',
        mensaje: `¡La dirección ingresada no es valida, actualizala para poder continuar con la compra!`,
        notificacion: false
      };

      modalRef.componentInstance.modal = infoModal;
      modalRef.componentInstance.tareaARealizar = () => modalRef.close();
    }

    const infoModal = {
      titulo: '¿Esta seguro?',
      mensaje: `¿Desea confirmar su pedido?<br><br>
      ¡Verifique que la dirección de envío, los libros seleccionados y el total a pagar sean correctos!`,
      notificacion: false
    };

    modalRef.componentInstance.modal = infoModal;
    modalRef.componentInstance.tareaARealizar = () => this.registrarVenta();
  }

}
