import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Post } from 'src/app/model/Post';
import { PostService } from 'src/app/_services/post.service';

@Component({
  selector: 'app-addpost',
  templateUrl: './addpost.component.html',
  styleUrls: ['./addpost.component.css']
})
export class AddpostComponent implements OnInit {

  constructor(private postService: PostService) { }

  model = new Post()

  addPostForm = new FormGroup({
    title: new FormControl('', [Validators.required]),
    content: new FormControl('', [Validators.required]),
    tag: new FormControl('', [Validators.required]),
  })

  submitted = false;

  onSubmit() {
    this.submitted = true;
  }


  ngOnInit(): void {
  }

  addPost(){
    let title = this.addPostForm.get("title")?.value
    let content = this.addPostForm.get("content")?.value
    let tag = this.addPostForm.get("tag")?.value

    this.postService.addPost(title, content, tag);
  }

}
