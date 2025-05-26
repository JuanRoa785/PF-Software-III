import { Component, OnInit } from '@angular/core';
import { CommonModule} from '@angular/common';
import { Router } from '@angular/router';
import { AppService } from '../../app.service';
import { TokenService } from '../../token.service';

@Component({
  selector: 'app-footer',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './footer.component.html',
  styleUrl: './footer.component.css'
})
export class FooterComponent implements OnInit{
  constructor(
    private router: Router,
    private appService: AppService,
    private tokenService:TokenService
  ) { }

  ngOnInit(): void {
    this.getGenerosLiterarios(); 
  }

  generosLiterarios: any[] = [];
  generosCol1: any[] = [];
  generosCol2: any[] = [];

  getGenerosLiterarios(): void {
    this.appService.getGenerosLiterarios().subscribe(
      (response) => {
        this.generosLiterarios = response; //Ya vienen ordenados
        this.generosLiterarios = this.generosLiterarios.slice(0, 12);
        this.generosCol1 = response.slice(0, 6);
        this.generosCol2 = response.slice(6, 12);
      },
      (error) => {
        console.error('Error cargando los generos Literarios:', error);
      }
    );
  }

  searchByGenero(genero: string) {
    this.router.navigate(['/buscarLibro'], { queryParams: { titulo: '', genero: genero } });
  }

  redirect(destino:string) {
    var userAuth:boolean = true;

    this.tokenService.isAuthenticated().subscribe(
      (isAuth) => {
        if (!isAuth) {
          userAuth = false;
        }
      }
    );

    switch (destino) {
      case 'home':
        this.router.navigate(['/']);
        break;
      case 'perfil':
        if (!userAuth) {
          this.router.navigate(['/login']);
        } else {
          this.router.navigate(['/cliente/perfil']);
        }
        break;
      case 'carrito':
        if (!userAuth) {
          this.router.navigate(['/login']);
        } else {
          this.router.navigate(['/cliente/carrito']);
        }
        break;
      case 'historial':
        if (!userAuth) {
          this.router.navigate(['/login']);
        } else {
          this.router.navigate(['/cliente/historialCompras']);
        }
        break;
      case 'admin':
        if (!userAuth) {
          this.router.navigate(['/login']);
        } else {
          this.router.navigate(['/admin/inventario']);
        }
        break;

      default:
        break;
    }
  }
}
