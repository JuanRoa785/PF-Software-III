import { Component } from '@angular/core';
import { CommonModule, NgClass } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AppService } from '../../../app.service';
import { Router } from '@angular/router';
import { UsuarioRegistro } from '../../../models/usuario-registro.model';

@Component({
  selector: 'app-sign-up-form',
  standalone: true,
  imports: [ 
    NgClass,
    CommonModule, 
    FormsModule,
  ],
  templateUrl: './sign-up-form.component.html',
  styleUrl: './sign-up-form.component.css'
})
export class SignUpFormComponent {
  constructor(
    private appService: AppService,
    private router: Router
  ) {}

  isPasswordVisible: boolean = false;
  isRepeatPasswordVisible:boolean = false;

  togglePasswordsVisibility(firstPassword:boolean) {
    if (firstPassword) {
      this.isPasswordVisible = !this.isPasswordVisible;
    } else {
      this.isRepeatPasswordVisible = !this.isRepeatPasswordVisible;
    }
  }

  regName:string = '';
  regLastName:string = '';
  regTelefono:string = '';
  regCorreo:string = '';
  regPassword:string = '';
  regRepeatPassword:string = '';

  isTerminosAceptados:boolean = false;
  isEmailValid:boolean = false;

  verifEmail() {
    if (this.regCorreo) {
      const regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
      this.isEmailValid = regex.test(this.regCorreo);
    } else {
      this.isEmailValid = false;
    }
  }

  errorEspecifico:string = '';

  camposAdecuados():boolean {
    this.regTelefono = this.regTelefono.replace(/\D/g, '');

    //Vacios
    if ([this.regName, this.regLastName, this.regCorreo, this.regTelefono, this.regPassword, 
      this.regRepeatPassword].some(value => value === '')) {
      this.errorEspecifico = '¡Hay por lo menos un campo vacio!'
      return false;
    }

    if (!this.isTerminosAceptados) {
      this.errorEspecifico = '¡Por favor acepte los terminos y condiciones!'
      return false;
    }

    //Invalidos
    if (!this.isEmailValid) {
      this.errorEspecifico = '¡El correo electrónico ingresado no es valido!'
      return false;
    }

    if (this.regTelefono.length != 10) {
      this.errorEspecifico = '¡El telefono debe tener exactamente 10 digitos!'
      return false;
    }

    if (this.regPassword.length < 8 || this.regRepeatPassword.length < 8) {
      this.errorEspecifico = '¡La contraseña debe tener mas de 8 caracteres!'
      return false;
    }

    if (this.regPassword != this.regRepeatPassword) {
      this.errorEspecifico = '¡Las contraseñas no coinciden!'
      return false;
    }

    return true;
  }

  isLoading:boolean = false;

  registrarse() {
    this.isLoading = true;
    this.errorEspecifico = '';
    if (this.camposAdecuados()) {
      const infoRegUser: UsuarioRegistro = {
        correo: this.regCorreo,
        contrasena: this.regPassword,
        name: this.regName,
        apellidos: this.regLastName,
        telefono: this.regTelefono
      };

      this.appService.registrarUsuario(infoRegUser).subscribe(
        (response) => {
          if (response) {
            this.router.navigate(['login/iniciarSesion']);
          }
        },
        (error) => {
          console.error('Error Registrando el usuario', error);
          this.errorEspecifico = '¡Puede que ya exista un usuario con ese correo, prueba otro!';
        }
      );
    }
    this.isLoading = false;
  }

}
