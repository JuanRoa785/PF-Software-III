import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { TokenService } from '../../token.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [RouterModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  constructor(
    private tokenService:TokenService,
    private router:Router
  ) {
    this.tokenService.isAuthenticated().subscribe(
      (isAuth) => {
        if (isAuth) {
          this.router.navigate(['/']);
        } 
      }
    )
  }
}
