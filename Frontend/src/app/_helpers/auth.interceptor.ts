import { HTTP_INTERCEPTORS, HttpEvent } from '@angular/common/http';
import { Injectable, OnInit } from '@angular/core';
import { HttpInterceptor, HttpHandler, HttpRequest } from '@angular/common/http';
import { TokenStorageService } from '../_services/token-storage.service';
import { Observable } from 'rxjs';
import { Router } from '@angular/router';

const TOKEN_HEADER_KEY = 'Authorization';       // for Spring Boot back-end

@Injectable()
export class AuthInterceptor implements HttpInterceptor, OnInit {

    isPopupShowed = false;
    token!: string;

    constructor(private tokenStorage: TokenStorageService, private router: Router) { }

    ngOnInit(): void {
        if (this.tokenExpired(this.token)) {
            this.showSessionExpiredPopUp();
          }
    }

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

        let authReq = req;
        this.token = <string>this.tokenStorage.getToken();

        if (!this.token) {
            return next.handle(req);
        }

        if (this.isTokenExpired(this.token)){
            window.sessionStorage.clear();
            if (window.location.href.slice(-4) != "home"){
                this.router.navigate(['/home']);
            }
        }

        if (this.token != null) {
            authReq = req.clone({
                headers: req.headers.set(TOKEN_HEADER_KEY, 'Bearer ' + this.token)
            });
        }

        

        return next.handle(authReq);
    }

    isTokenExpired(token: string | null) {
        if (this.tokenExpired(this.token)) {
            return true;
        }
        return false;
    }

    showSessionExpiredPopUp() {
        alert("Your token expired");
        window.location.reload();
    }

    private tokenExpired(token: string) {
        const expiry = (JSON.parse(atob(token.split('.')[1]))).exp;
        return (Math.floor((new Date).getTime() / 1000)) >= expiry;
      }

}
export const authInterceptorProviders = [
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true }
];