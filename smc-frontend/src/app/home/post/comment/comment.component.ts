import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { CommentModel } from 'src/app/models/commentModel';
import { PostModel } from 'src/app/models/postModel';
import { PostService } from 'src/app/services/post.service';
import { CommentPayload } from '../commentPayload';

@Component({
  selector: 'app-comment',
  templateUrl: './comment.component.html',
  styleUrls: ['./comment.component.css']
})
export class CommentComponent implements OnInit {

  @Input() currentPost: PostModel;
  @Output() addedComment: EventEmitter<any> =  new EventEmitter<any>();
  @Output() deletedComment: EventEmitter<any> =  new EventEmitter<any>();
  showCommentsField: boolean = false;
  comments: CommentModel[];
  commentForm: FormGroup
  commentPaylaod: CommentPayload;
  constructor(private postService: PostService) {
    this.commentPaylaod = {
      text: ''
    }
  }
  ngOnInit(): void {
    console.log(this.currentPost.id);
    this.postService.getComments(this.currentPost.id).subscribe({
      next: (data) => {
        this.comments = data;
      }
    })
    this.commentForm = new FormGroup({
      text: new FormControl('', Validators.required)
    });
  }

  showComments() {
    this.showCommentsField = !this.showCommentsField
  }

  deleteComment(comment: CommentModel) {
    this.postService.deleteComment(this.currentPost.id, comment.commentId).subscribe({
      next: (data) => {
        // this.comments = this.comments.filter((c) => c.commentId !== comment.commentId);
        this.comments = data;
        this.deletedComment.emit();
      }
    })
  }

  postComment() {
    this.commentPaylaod.text = this.commentForm.get('text')?.value;
    this.postService.addComment(this.currentPost.id, this.commentPaylaod).subscribe({
      next: (data) => {
        // const comment: CommentModel = {
        //   commentId: data.commentId,
        //   user: data.user,
        //   text: data.text,
        //   post: data.post
        // }
        // console.log(comment);
        this.comments.push(data);
        this.addedComment.emit();
      }
    });
    this.commentForm.reset();

  
  }











  // @Input() postId: number;
  // comments: CommentModel[];
  // commentForm: FormGroup;
  // commentPaylaod: CommentPayload;
  // showCommentsField: boolean = false;
  // constructor(private postService: PostService) { 
  //   console.log(this.postId)
  //   this.postService.getComments(this.postId).subscribe({
  //     next: (data) => {
  //       this.comments = data;
  //     }
  //   })
  //   this.commentPaylaod = {
  //     text: ''
  //   }
  //   this.commentForm = new FormGroup({
  //     text: new FormControl('', Validators.required)
  //   });
  // }

  // deleteComment(comment: CommentModel) {
  //   this.postService.deleteComment(this.postId, comment.commentId).subscribe({
  //     next: () => {
  //       this.comments = this.comments.filter((c) => {
  //         c.commentId !== comment.commentId;
  //       })
  //     }
  //   })
  // }

  // postComment() {
  //   this.commentPaylaod.text = this.commentForm.get('text')?.value;
  //   this.postService.addComment(this.postId, this.commentPaylaod).subscribe({
  //     next: () => {
        
  //     }
  //   });
  // }
  // showComments() {
  //   console.log('hereaaaa')
  //   this.showCommentsField = !this.showCommentsField
  // }
}
