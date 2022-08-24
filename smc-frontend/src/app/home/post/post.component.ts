import { Component, Input, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { PostModel } from 'src/app/models/postModel';
import { UserModel } from 'src/app/models/userModel';
import { PostService } from 'src/app/services/post.service';
import { CommentPayload } from './commentPayload';

@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.css']
})
export class PostComponent implements OnInit {

  @Input() post: PostModel;
  currentUser: UserModel;
  likeCount: number;
  commentCount: number;
  showLike: boolean = true;

  constructor(private postService: PostService) {

    this.postService.getCurrentUser().subscribe({
      next: (data) => {
        this.currentUser = data;
      }
    })

  }

  ngOnInit(): void {
      this.postService.hasLiked(this.post.id).subscribe({
        next: (data) => {
        this.showLike = !data;
      }
    });
    this.likeCount = this.post.likes.length;
    this.commentCount = this.post.comments.length;
  }
  like() {
    this.postService.like(this.post.id).subscribe({
      next: () => {
        this.showLike = !this.showLike
        this.likeCount++;
      }
    });
  }
  dislike() {
    this.postService.dislike(this.post.id).subscribe({
      next: () => {
        this.showLike = !this.showLike
        this.likeCount--;
      }
    });
  }

  delete() {
    this.postService.deletePost(this.post.id).subscribe({
      next: () => {
        window.location.reload();
      }
    });
  }

  addedAComment() {
    this.commentCount++;
  }
  deletedAComment() {
    this.commentCount--;
  }
}