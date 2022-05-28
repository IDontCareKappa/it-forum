import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

const AUTH_API = 'http://localhost:8080/api/';
const httpLoginOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/x-www-form-urlencoded' })
};

@Injectable({
  providedIn: 'root'
})

export class AuthService {

  constructor(private http: HttpClient) { }

  login(username: string, password: string): Observable<any> {
    let body = new URLSearchParams();
    body.set('username', username);
    body.set('password', password);

    return this.http.post(AUTH_API + 'login', body.toString(), httpLoginOptions);
  }

  register(username: string, password: string, email: string): Observable<any> {
    return this.http.post(AUTH_API + 'user/save', {
      username,
      password,
      email
    });
  }

}