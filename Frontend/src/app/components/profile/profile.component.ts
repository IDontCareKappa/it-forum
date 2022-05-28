import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/model/User';
import { TokenStorageService } from 'src/app/_services/token-storage.service';

const API_URL = 'http://localhost:8080/api/';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})

export class ProfileComponent implements OnInit {

  currentUser: User = new User();
  userPosts = 0

  constructor(private http: HttpClient, private token: TokenStorageService) { }
  
  ngOnInit(): void {
    this.currentUser.username = this.token.getUser().sub;
    this.currentUser.roles = this.token.getUser().roles;

    this.http.get<number>(API_URL + 'user/' + this.currentUser.username)
    .subscribe(data => {
      this.userPosts = data;
    },
    err => {
      alert(err.error.message); 
    });
  }

}