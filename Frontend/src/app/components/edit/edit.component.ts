import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Post } from 'src/app/model/Post';
import { PostService } from 'src/app/_services/post.service';
import { TokenStorageService } from 'src/app/_services/token-storage.service';
import { UserService } from 'src/app/_services/user.service';


@Component({
  selector: 'app-edit',
  templateUrl: './edit.component.html',
  styleUrls: ['./edit.component.css']
})
export class EditComponent implements OnInit {

  data: Post = new Post;
  username!: string;
  id!: string;
  isLoggedIn = false;
  errors = false;
  roles: string[] = []

  form: any = {
    question: null,
    description: null,
    tag: null
  };

  model = new Post()


  constructor(public userService: UserService,
    public postService: PostService,
    private _Activatedroute: ActivatedRoute) { }

  ngOnInit(): void {
    var helper = this._Activatedroute.snapshot.paramMap.get("id");

    if (helper) {
      this.id = helper;
    }

    this.postService.getPost(this.id).subscribe(
      data => {
        this.data = data;
      },
      err => {
        this.data = err.error.message;
      }
    );
  }

  savePost() {
    let title = this.form.question
    let content = this.form.description
    let tag = this.form.tag

    if (!title){
      title = this.data.title;
    }
    if (!content){
      content = this.data.content;
    }
    if (!tag){
      tag = this.data.tag;
    }

    this.postService.updatePost(this.id, title, content, tag)

  }


}
