import { Component } from '@angular/core';
import { CommonModule, NgClass } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AppService } from '../../../app.service';
import { Router } from '@angular/router';
import { TokenService } from '../../../token.service';

@Component({
  selector: 'app-login-form',
  standalone: true,
  imports: [ 
    NgClass,
    CommonModule, 
    FormsModule,
  ],
  templateUrl: './login-form.component.html',
  styleUrl: './login-form.component.css'
})
export class LoginFormComponent {
  constructor(
    private appService: AppService,
    private router: Router,
    private tokenService:TokenService
  ) {}

  isPasswordVisible: boolean = false;
  togglePasswordsVisibility() {
    this.isPasswordVisible = !this.isPasswordVisible;
  }

  logPassword:string = '';
  logCorreo:string = '';
  islogCorreoValid:boolean = false;

  verifCorreo() {
    if (this.logCorreo) {
      const regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
      this.islogCorreoValid = regex.test(this.logCorreo);
    } else {
      this.islogCorreoValid = false;
    }
  }

  errorEspecifico:string = '';

  camposAdecuados():boolean {
    //Vacios
    if (this.logCorreo == '' || this.logPassword == '') {
      this.errorEspecifico = '¡Hay por lo menos un campo vacio!'
      return false;
    }

    //Invalidos
    if (!this.islogCorreoValid) {
      this.errorEspecifico = '¡El correo electrónico ingresado no es valido!'
      return false;
    }

    if (this.logPassword.length < 8) {
      this.errorEspecifico = '¡La contraseña debe tener mas de 8 caracteres!'
      return false;
    }

    return true;
  }

  isLoading:boolean = false;

  iniciarSesion() {
    this.isLoading = true;
    this.errorEspecifico = '';
    if (this.camposAdecuados()) {
      this.appService.iniciarSesion(this.logCorreo, this.logPassword).subscribe(
        (response:any) => {
          if (response) {
            //console.log(response)
            this.tokenService.setToken(response.access_token);
            window.location.href = '/';
          }
        },
        (error) => {
          console.error('Error Iniciando Sesión', error);
          this.errorEspecifico = '¡Usuario o Contraseña Incorrectos!';
        }
      );
    }
    this.isLoading = false;
  }
}
