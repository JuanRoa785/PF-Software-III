import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { CommonModule } from '@angular/common';
import { TokenService } from '../../token.service';
import { NgbCollapseModule } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [FormsModule, CommonModule, NgbCollapseModule],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent implements OnInit{
  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private tokenService: TokenService
  ) { }

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.tituloLibro = params['titulo'];
    });
    this.getUserRole();
  }

  tituloLibro:string = "";

  onKeydown(event: KeyboardEvent): void {
    if (event.key === 'Enter') {
      this.onSearch();
    }
  }
  
  onSearch() {
    this.router.navigate(['/buscarLibro'], { queryParams: { titulo: this.tituloLibro, genero: "" } });
  }

  rol:string = '2';

  getUserRole(): void {
    this.tokenService.getUserRole().subscribe(
      (response) => {
        //console.log(response);
        this.rol = response;
      },
      (error) => {
        console.error('Error obteniendo el rol del usuario:', error);
      }
    );
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

  isNavbarCollapsed = true;
  
}
