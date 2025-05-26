import { Injectable } from '@angular/core';
import { jwtDecode } from "jwt-decode";
import { HttpClient } from '@angular/common/http';
import { environment } from '../environment';
import { HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { map, catchError } from 'rxjs/operators';
import { User } from './models/usuario.model';

@Injectable({
  providedIn: 'root'
})
export class TokenService {
  constructor(private http: HttpClient) { }
  url = environment.Url;
  private tokenKey: string = 'token';

  setToken(token: string) {
    localStorage.setItem(this.tokenKey, token);
  }

  getToken(): string | null {
    return localStorage.getItem(this.tokenKey);
  }

  clearToken() {
    localStorage.removeItem(this.tokenKey);
  }

  getUserEmail(): User | null{
    var token = this.getToken();
    if (token != null) {
      //console.log(jwtDecode(token));
      return jwtDecode<User>(token);
    }
    else {
      return null;
    }
  }

  getUserRole(): Observable<string> {
    if (!this.getToken() || !this.getUserEmail()) {
      return of('2'); // valor por defecto
    }

    return this.getUser().pipe(
      map((response) => response.tipoUsuario.id.toString()),
      catchError((error) => {
        this.clearToken();
        return of('2');
      })
    );
  }


  getUser(): Observable<any> {
    const token = this.getToken();
    const correo = this.getUserEmail()?.sub || 'n.a';

    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}` 
    });

    const params = new HttpParams()
      .set('email', correo);

    //console.log(token);
    return this.http.get(this.url + '/auth/listByEmail', { headers, params });
  }

  isAuthenticated(): Observable<boolean> {
    if (this.getToken() == null || !this.getUserEmail()) {
      return of(false);
    } else {
      return this.getUser().pipe(
        map((response) => {
          //console.log(response)
          return true;
        }),
        catchError((error) => {
          this.clearToken();
          return of(false);
        })
      );
    }
  }
}
