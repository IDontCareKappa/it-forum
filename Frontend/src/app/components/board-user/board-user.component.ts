import { Component, OnInit } from '@angular/core';
import { Post } from 'src/app/model/Post';
import { PostService } from 'src/app/_services/post.service';


@Component({
  selector: 'app-board-user',
  templateUrl: './board-user.component.html',
  styleUrls: ['./board-user.component.css']
})
export class BoardUserComponent implements OnInit {

  content?: Post[];

  constructor(public postService: PostService) { }

  ngOnInit(): void {

    this.postService.getUserPosts().subscribe(
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
