import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { TokenStorageService } from './token-storage.service';
import { map } from 'rxjs/operators';
import { Observable } from 'rxjs';
import { Post } from '../model/Post';

const API_URL = 'http://localhost:8080/api/';

@Injectable({
  providedIn: 'root'
})
export class PostService {

  username!: string;

  constructor(private http: HttpClient, private tokenStorage: TokenStorageService) { 
    this.username = this.tokenStorage.getUser().sub;
  }

  getPost(id: string): Observable<any>{
    return this.http.get(API_URL + 'post/' + id).pipe(
        map(response => response)
        );
  }

  getPostsByTag(tag: string): Observable<any>{
    return this.http.get(API_URL + 'posts/' + tag).pipe(
      map(response => response)
      );
  }

  getUserPosts(): Observable<any>{
    return this.http.get<Post[]>(API_URL + 'posts/user/' + this.username)
    .pipe(
      map(response => response)
      );
  }
  

  addPost(title: string, content: string, tag: string){
    return this.http.post(API_URL + 'post/save', {
      "title": title,
      "content": content,
      "author": this.username,
      "tag": tag
    }).subscribe(data => {
      window.location.reload();
      alert("Successfuly added post")
    },
    err => {
      alert(err.error.message); 
    });
  }

  updatePost(id: string, title: string, content: string, tag: string){
    return this.http.put(API_URL + 'post/update/' + id, {
      "title": title,
      "content": content,
      "author": this.username,
      "tag": tag
    }).subscribe(data => {
      window.location.reload();
      alert("Successfuly edited post")
    },
    err => {
      alert(err.error.message); 
    });
  }

  deletePost(id: number){
    return this.http.delete(`${API_URL}post/delete/${id}`).subscribe(data => {
      window.location.reload();
    },
    err => {
      alert(err.error.message); 
    });
  }

  deleteComment(id: number){
    return this.http.delete(`${API_URL}comment/${id}`).subscribe(data => {
      window.location.reload();
    },
    err => {
      alert(err.error.message); 
    });
  }

  addThumbUp(commentId: number, username: string){
    return this.http.post(`${API_URL}comment/plus`, {username, commentId}).subscribe(data => {
      window.location.reload();
    },
    err => {
      alert(err.error.message); 
    });
  }

  addThumbDown(commentId: number, username: string){
    return this.http.post(`${API_URL}comment/minus`, {username, commentId}).subscribe(data => {
      window.location.reload();
    },
    err => {
      alert(err.error.message); 
    });
  }

  getTags(): Observable<any>{
    return this.http.get(API_URL + 'tags').pipe(
      map(response => response)
      );
  }

  addComment(content: string, postId: string){
    return this.http.post(API_URL + 'comment', {
      "username": this.username,
      "content": content,
      "postId": postId
    }).subscribe(data => {
      window.location.reload();
    },
    err => {
      alert(err.error.message); 
    });
  }

  markAsCorrect(id: number) {
    return this.http.post(API_URL + 'comment/correct/' + id, {})
    .subscribe(data => {
      window.location.reload();
    },
    err => {
      alert(err.error.message); 
    });
  }

}
