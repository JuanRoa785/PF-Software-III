import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AppService } from '../../app.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ModalVerifComponent } from '../../components/modal-verif/modal-verif.component';

@Component({
  selector: 'app-crud-libro',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './crud-libro.component.html',
  styleUrl: './crud-libro.component.css'
})
export class CrudLibroComponent {
  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private appService:AppService,
    private modalService:NgbModal
  ) { }

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.idLibro = params['idLibro'];
    });
    this.getLibroInfo();
    this.getEditoriales();
    this.getGenerosLiterarios();
  }

  idLibro:number = 0;
  libro:any = {}

  getToday():string {
    const today = new Date();
    const day = String(today.getDate()).padStart(2, '0');
    const month = String(today.getMonth() + 1).padStart(2, '0'); // ¡Enero es 0!
    const year = today.getFullYear();

    const fecha = `${year}-${month}-${day}`;
    //console.log(fecha)
    return fecha;
  }

  getLibroInfo() {
    if (this.idLibro == 0) {
      this.libro = {
        id: this.idLibro,
        generoLiterarioId: 1,
        nombre: '',
        sinopsis: '',
        id_portada: '',
        portada: '',
        precio: 0,
        impuesto: 0,
        stock: 0,
        autor: '',
        editorial: 'Planeta',
        edicion: 'Tapa Dura',
        fecha_publicacion: this.getToday(),
        idioma: 'Español',
        numero_paginas: 0,
        dimensiones: 0,
        coleccion: ''
      };
      return;
    }

    this.appService.getLibroByID(this.idLibro).subscribe(
      (response) => {
        this.libro = response; 
        this.libro.fecha_publicacion = this.libro.fecha_publicacion.slice(0,10);
        this.libro['generoLiterarioId'] = this.libro.generoLiterario.id;
        this.imagenSeleccionada = this.libro['portada']
        //console.log(this.libro);
        //console.log(response);
      },
      (error) => {
        console.error('Error cargando el libro:', error);
      }
    );
  }

  generos:any[] = [];

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

  editoriales: string[] = []
  getEditoriales() {
    this.appService.getEditoriales().subscribe(
      (response:string[]) => {
        this.editoriales =  response;
      },
      (error) => {
        console.error('Error cargando los generos Literarios mas recientes:', error);
      }
    );
  }

  strError = '';

  camposValidos(): boolean {
    this.strError = ''
    //Nulos -> String
    if (this.libro.nombre == '' || this.libro.sinopsis == '' || //this.libro.portada == '' ||
      this.libro.autor == '' || this.libro.coleccion == '' || this.libro.editorial == '') {
      this.strError = '¡El nombre, la sinopsis, la url de la portada, el autor, la editorial o la coleccion estan vacios!';
      return false;
    }

    if (this.imagenSeleccionada == null) {
      this.strError = '¡La Portada es invalida!';
      return false;
    }

    if (this.libro.precio <= 0 ||  this.libro.impuesto <= 0 || this.libro.impuesto > 1 || 
      this.libro.stock < 0 || this.libro.numero_paginas <= 0 ) {
      this.strError = 'El precio, el impuesto, el stock o el número de paginas no es valido';
      return false;
    }

    return true;
  }

  imagenSeleccionada!: File;
  tempUrlnewImg:string = '';
  isLoading: boolean = false;

  onFileChange(event: any) {
    this.imagenSeleccionada = event.target.files[0];
    this.tempUrlnewImg = URL.createObjectURL(this.imagenSeleccionada);
  }

  gestionarLibro() {
    if (!this.camposValidos()) {
      return;
    }

    this.isLoading = true;

    this.appService.gestionarLibro(this.libro, this.imagenSeleccionada).subscribe(
      (response) => {
        const modalRef = this.modalService.open(
          ModalVerifComponent, {
          backdrop: 'static',
          centered: true
        }
        );

        const infoModal = {
          titulo: 'Operación Exitosa',
          mensaje: 'Se actualizó la información correctamente, puede verificarla en el inventario',
          notificacion: true
        };

        modalRef.componentInstance.modal = infoModal;
        modalRef.componentInstance.tareaARealizar = () => this.router.navigate(['/admin/inventario']);
        this.isLoading = false;
      },
      (error) => {
        const modalRef = this.modalService.open(
          ModalVerifComponent, {
          backdrop: 'static',
          centered: true
        }
        );

        const infoModal = {
          titulo: '¡Ha ocurrido un error!',
          mensaje: 'Verifica que la imagen cargada no supere los 10MB e intentalo nuevamente',
          notificacion: true
        };

        modalRef.componentInstance.modal = infoModal;
        modalRef.componentInstance.tareaARealizar = () => modalRef.close();

        console.error('Error gestionando el libro:', error);
        this.isLoading = false;
      }
    );
  }
}
