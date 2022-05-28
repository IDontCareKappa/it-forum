import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { TokenStorageService } from '../_services/token-storage.service';
import { map } from 'rxjs/operators';
import { Post } from '../model/Post';


const API_URL = 'http://localhost:8080/api/';
@Injectable({
  providedIn: 'root'
})



export class UserService {

  constructor(private http: HttpClient, private tokenStorage: TokenStorageService) { }


  getPublicContent(): Observable<any> {
    return this.http.get<Post[]>(API_URL + 'posts')
    .pipe(
      map(response => response)
      );
  }

  getUserBoard(): Observable<any> {
    return this.http.get(API_URL + 'user', { responseType: 'json' });
  }

}