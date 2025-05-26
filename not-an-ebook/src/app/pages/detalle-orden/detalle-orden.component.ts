import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-detalle-orden',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './detalle-orden.component.html',
  styleUrl: './detalle-orden.component.css'
})
export class DetalleOrdenComponent {

  @Input() orden!: {
    detalleVenta: Array<{
      cantidad: number;
      id: number;
      idVenta: number;
      libro: {
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
        numero_paginas: string;
        dimensiones: string;
        coleccion: string;
      };
    }>;
    direccion: string;
    fecha: string;
    id: number;
    observaciones: string;
    total: number;
    usuario: {
      id: number;
      nombres: string;
      apellidos: string;
      email: string;
      telefono: string;
      cuenta_activa: boolean;
      tipoUsuario: {
        id: number;
        nombre: string;
        descripcion: string;
      };
    };
  }

  formatDate(dateString: string): string[] {
    const date = new Date(dateString);
    
    // Extraer componentes de fecha y hora
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0'); // Mes comienza en 0
    const day = String(date.getDate()).padStart(2, '0');
    const hours = String(date.getHours()).padStart(2, '0');
    const minutes = String(date.getMinutes()).padStart(2, '0');
    
    // Formato deseado: YYYY/MM/DD HH:MM
    //return `${year}/${month}/${day} ${hours}:${minutes}`;
    return [`${year}/${month}/${day}`, `${hours}:${minutes}`];
  }

  formatTotal(): string {
    const total = Math.round(this.orden.total);
    return total.toLocaleString('es-CO', { minimumFractionDigits: 0, maximumFractionDigits: 0 });
  }

  formatAdress(): string[] {
    return this.orden.direccion.split('-');
  }

  getPrecioImpuesto(libro: any): string {
    const getPrecioImpuesto = libro.precio + (libro.precio * libro.impuesto); 
    const precioIRedondeado = Math.round(getPrecioImpuesto);
    return precioIRedondeado.toLocaleString('es-CO', { minimumFractionDigits: 0, maximumFractionDigits: 0 });
  }
  
  getSubTotalLibro(detalleVenta:any): string {
    var subtotal = detalleVenta.cantidad * ((detalleVenta.libro.precio)*(1 + detalleVenta.libro.impuesto))
    return (Math.round(subtotal)).toLocaleString('es-CO', { minimumFractionDigits: 0, maximumFractionDigits: 0 });
  }

  @Output() closeDetail = new EventEmitter<void>();

  redirectTo() {
    this.closeDetail.emit();
  }
}
