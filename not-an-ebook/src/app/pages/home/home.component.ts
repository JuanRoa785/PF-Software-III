import { Component, OnInit } from '@angular/core';
import { CommonModule} from '@angular/common';
import { CardLibroComponent } from '../../components/card-libro/card-libro.component';
import { AppService } from '../../app.service';
import { Router } from '@angular/router';
import { NgbCarousel } from '@ng-bootstrap/ng-bootstrap';
import { NgbCarouselModule } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CardLibroComponent, CommonModule, NgbCarouselModule],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent implements OnInit{
  constructor(
    private router: Router, 
    private appService: AppService
  ) {};

  latestLibros: any[] = [];
  librosRow1: any[] = [];
  librosRow2: any[] = [];

  generosLiterarios: any[] = [];
  carruselMainSlide: any[] = [];
  carrusel2ndSlide: any[] = [];

  ngOnInit(): void {
    this.getLatestLibros();
    this.getGenerosLiterarios(); 
  }
  
  getLatestLibros(): void {
    this.appService.getLibros().subscribe(
      (response) => {
        this.latestLibros = response.sort((a: any, b: any) => 
          new Date(b.fecha_publicacion).getTime() - new Date(a.fecha_publicacion).getTime()); //Ordena de mas reciente a mas viejo
        this.latestLibros = this.latestLibros.slice(0, 10);
        this.librosRow1 = response.slice(0, 5);
        this.librosRow2 = response.slice(5, 10);
      },
      (error) => {
        console.error('Error cargando los libros mas recientes:', error);
      }
    );
  }

  getGenerosLiterarios(): void {
    this.appService.getGenerosLiterarios().subscribe(
      (response) => {
        this.generosLiterarios = response; //Ya vienen ordenados
        this.generosLiterarios = this.generosLiterarios.slice(0, 12);
        this.carruselMainSlide = response.slice(0, 6);
        this.carrusel2ndSlide = response.slice(6, 12);
      },
      (error) => {
        console.error('Error cargando los generos Literarios mas recientes:', error);
      }
    );
  }

  searchByGenero(genero: string) {
    this.router.navigate(['/buscarLibro'], { queryParams: { titulo: '', genero: genero } });
  }
}