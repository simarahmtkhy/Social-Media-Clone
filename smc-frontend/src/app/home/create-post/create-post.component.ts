import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormControl, FormGroup, NonNullableFormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { PostModel } from 'src/app/models/postModel';
import { PostService } from 'src/app/services/post.service';
import { PostPayload } from './postPayload';

@Component({
  selector: 'app-create-post',
  templateUrl: './create-post.component.html',
  styleUrls: ['./create-post.component.css']
})
export class CreatePostComponent implements OnInit {

  postPayload: PostPayload
  createPostForm: FormGroup;
  file: File;
  imageId: number
  videoId: number
  postModelTemp: PostModel;
  temp: boolean;
  @ViewChild('fileInput') fileInput: ElementRef;
  constructor(private postService: PostService, private router: Router) { 
    this.postPayload = {
      body: {
        text: ''
      }
    }
  }

  ngOnInit(): void {
    this.createPostForm = new FormGroup({
      text: new FormControl('')
    })
    this.temp = false;
  }

  createPost(){
    this.postPayload.body.text = this.createPostForm.get('text')?.value
    console.log(this.postPayload);
    this.postService.createPost(this.postPayload).subscribe({
      next: (data) => {
        console.log(data);
        this.postModelTemp = data;
        this.temp = true;
      }
    });
    this.createPostForm.reset();
    this.fileInput.nativeElement.value = "";
  }

  uploadFile(event: Event) {
    if ((event.target as HTMLInputElement).files?.item(0) !== (null || undefined)) {
      this.file = (event.target as HTMLInputElement).files?.item(0)!;
      return this.postService.uploadFile(this.file).subscribe({
        next: (data) => {
          if (this.file.type.startsWith('image')) {
            this.imageId = data
            this.postPayload.body.image = {
              id: this.imageId
            }
          }
          else {
            this.videoId = data
            this.postPayload.body.video = {
              id: this.videoId
            }
          }

        }
      }
      );
    }
    else {
      return null;
    } 
  }

  reset() {
    this.temp = false;
  }

}
