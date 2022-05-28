import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { Post } from 'src/app/model/Post';
import { Comment } from 'src/app/model/Comment';
import { UserService } from 'src/app/_services/user.service';
import { PostService } from 'src/app/_services/post.service';
import { TokenStorageService } from 'src/app/_services/token-storage.service';


const API_URL = 'http://localhost:8080/api/';

@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.css']
})
export class PostComponent implements OnInit {

  content: Post = new Post;
  comments: Comment[] = [];
  username!: string;
  id!: string;
  isLoggedIn = false;
  roles: string[] = []
  hasRoleAdmin = false;

  model = new Comment()

  constructor(private http: HttpClient, 
    public userService: UserService, 
    public postService: PostService, 
    private tokenStorageService: TokenStorageService, 
    private _Activatedroute: ActivatedRoute) {

  }



  ngOnInit(): void {
    this.isLoggedIn = !!this.tokenStorageService.getToken();
    var helper = this._Activatedroute.snapshot.paramMap.get("id");
    if (helper) {
      this.id = helper;
    }
    this.postService.getPost(this.id).subscribe(
      data => {
        this.content = data;
        this.comments = data.comments;
        this.comments.sort((a,b) => a.correct > b.correct ? -1 : 1)
      },
      err => {
        this.content = err.error.message;
      }

    );

    this.username = this.tokenStorageService.getUser().sub;
    this.roles = this.tokenStorageService.getUser().roles;

    this.hasRoleAdmin = this.roles.includes('ROLES_ADMIN');

  }

  onDelete(id: number) {
    this.postService.deleteComment(id);
    
  }

  thumbUp(commentId: number){
    this.postService.addThumbUp(commentId, this.username);
    
  }

  thumbDown(commentId: number){
    this.postService.addThumbDown(commentId, this.username);
    
  }

  addComment(){
    this.postService.addComment(this.model.content, this.id);
  }
  
  markAsCorrect(commentId: number){
    this.postService.markAsCorrect(commentId);
  }
}
