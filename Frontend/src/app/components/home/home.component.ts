import { Component, OnInit } from '@angular/core';
import { Post } from 'src/app/model/Post';
import { PostService } from 'src/app/_services/post.service';
import { TokenStorageService } from 'src/app/_services/token-storage.service';
import { UserService } from 'src/app/_services/user.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})

export class HomeComponent implements OnInit {

  content?: Post[];
  username?: string;
  isLoggedIn = false;
  tags?: string[];
  roles: string[] = [];
  hasRoleAdmin = false;

  constructor(private userService: UserService,
    public postService: PostService,  
    private tokenStorageService: TokenStorageService) { }

  ngOnInit(): void {

    this.isLoggedIn = !!this.tokenStorageService.getToken();

    this.userService.getPublicContent().subscribe(
      data => {
        this.content = data;
      },
      err => {
        this.content = err.error.message;
      }
    );

    this.postService.getTags().subscribe(
      data => {
        this.tags = data;
      },
      err => {
        this.content = err.error.message;
      }
    );

    this.username = this.tokenStorageService.getUser().sub;
    this.roles = this.tokenStorageService.getUser().roles;

    for (let i = 0; i < this.roles.length; i++){
      if (this.roles[i] == "ROLE_ADMIN"){
        this.hasRoleAdmin = true;
      }
    }

  }

  getPostsByTag(tag: string){
    this.postService.getPostsByTag(tag)
    .subscribe(
      data => {
        this.content = data;
      },
      err => {
        this.content = err.error.message;
      }
    );
  }

  deletePost(id: number){
    this.postService.deletePost(id);
    window.location.reload();
  }

}