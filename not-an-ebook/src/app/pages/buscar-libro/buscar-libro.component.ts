import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { AppService } from '../../app.service';
import { CardLibroComponent } from '../../components/card-libro/card-libro.component';
import { NgbPagination } from '@ng-bootstrap/ng-bootstrap';
import { ViewportScroller } from '@angular/common';

@Component({
  selector: 'app-buscar-libro',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    CardLibroComponent,
    NgbPagination
  ],
  templateUrl: './buscar-libro.component.html',
  styleUrl: './buscar-libro.component.css'
})
export class BuscarLibroComponent {
  constructor(
    private route: ActivatedRoute, 
    private appService: AppService,
    private viewportScroller: ViewportScroller
  ) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      if (params['titulo'] != undefined) {
        this.tituloLibro = params['titulo'];
      }
      this.selectedGenero = params['genero'];
      this.getGenerosLiterarios();
      this.getEditoriales();
      this.getLibrosFiltrados();
      this.viewportScroller.scrollToPosition([0, 0]);
    });
  }

  tituloLibro:string = ''
  selectedSortFecha:string = '1'
  selectedGenero:string = ''
  generosLiterarios:any = []

  getGenerosLiterarios(): void {
    this.appService.getGenerosLiterarios().subscribe(
      (response) => {
        this.generosLiterarios = response; //Ya vienen ordenados
      },
      (error) => {
        console.error('Error cargando los generos Literarios mas recientes:', error);
      }
    );
  }

  editoriales: { nombre: string; checked: boolean }[] = []
  otrasEditoriales:string[] = [];
  selectedEditoriales: string[] = [];

  getEditoriales() {
    this.appService.getEditoriales().subscribe(
      (response:string[]) => {
        //Mapeamos las primeras 9 editoriales como checkboxes
        response.slice(0,9).forEach(editorial => {
          this.editoriales.push({nombre:editorial, checked: false})
        });

        //Agregamos hardCoded la opción de otras:
        this.editoriales.push({nombre:'Otras', checked: false})

        this.otrasEditoriales = response.slice(9) //Resto de editoriales
        //console.log(this.otrasEditoriales)
      },
      (error) => {
        console.error('Error cargando los generos Literarios mas recientes:', error);
      }
    );
  }

  updateEditorialesSelected(editorial: { nombre: string; checked: boolean }): void {
    editorial.checked = !editorial.checked
    if (editorial.checked == true) {
      if (!this.selectedEditoriales.includes(editorial.nombre)) {
        this.selectedEditoriales.push(editorial.nombre)
        if (editorial.nombre == 'Otras') {
          this.selectedEditoriales = this.selectedEditoriales.concat(this.otrasEditoriales);
        }
        this.getLibrosFiltrados();
      }
    } else {
      if (this.selectedEditoriales.includes(editorial.nombre)) {
        const index = this.selectedEditoriales.indexOf(editorial.nombre);
        this.selectedEditoriales.splice(index, 1);
        if (editorial.nombre == 'Otras') {
          this.selectedEditoriales = this.selectedEditoriales.filter(editorial => !this.otrasEditoriales.includes(editorial));
        }
        this.getLibrosFiltrados();
      }
    }
  }

  librosFiltrados: any[] = []

  getLibrosFiltrados(): void {
    this.appService.getLibrosFlitrados(this.tituloLibro, this.selectedGenero).subscribe(
      (response) => {
        if (this.selectedSortFecha.match('2')) {
          this.librosFiltrados = response.sort((a: any, b: any) =>
             new Date(a.fecha_publicacion).getTime() - new Date(b.fecha_publicacion).getTime());;
        } else {
          this.librosFiltrados = response
        }
        
        if (this.selectedEditoriales.length != 0) {
          this.librosFiltrados = this.librosFiltrados.filter(libro =>
            this.selectedEditoriales.includes(libro.editorial)
          );
        }
      },
      (error) => {
        console.error('Error cargando los libros filtrados:', error);
      }
    );
  }

  currentPage: number = 1;
  itemsPerPage: number = 9;

  get paginatedLibros() {
    const startIndex = (this.currentPage - 1) * this.itemsPerPage;
    return this.librosFiltrados.slice(startIndex, startIndex + this.itemsPerPage);
  }

  onPageChange(page: number): void {
    this.currentPage = page;
  }

}
