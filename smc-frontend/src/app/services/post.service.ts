import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { PostPayload } from '../home/create-post/postPayload';
import { CommentPayload } from '../home/post/commentPayload';
import { CommentModel } from '../models/commentModel';
import { PostModel } from '../models/postModel';
import { UserModel } from '../models/userModel';

@Injectable({
  providedIn: 'root'
})
export class PostService {

  constructor(private httpClient: HttpClient) { }

  formData: FormData = new FormData();

  currentPost: PostModel;

  createPost(postPayload: PostPayload): Observable<any> {
    return this.httpClient.post('http://localhost:8080/app/post', postPayload, {responseType: 'text'});
  }

  uploadFile(file: File): Observable<number> {
    this.formData.set('file', file);
    return this.httpClient.post<number>('http://localhost:8080/app/upload', this.formData);
  }

  like(postId: number): Observable<any> {
    return this.httpClient.post('http://localhost:8080/app/posts/like/' + postId, null);
  }
  dislike(postId: number): Observable<any> {
    return this.httpClient.delete('http://localhost:8080/app/posts/dislike/' + postId);
  }
  hasLiked(postId: number): Observable<boolean> {
    return this.httpClient.get<boolean>('http://localhost:8080/app/posts/hasliked/' + postId);
  }
  addComment(postId: number, comment: CommentPayload): Observable<CommentModel> {
    return this.httpClient.post<CommentModel>('http://localhost:8080/app/posts/comment/' + postId, comment);
  }
  getCurrentUser(): Observable<UserModel> {
    return this.httpClient.get<UserModel>('http://localhost:8080/app/user');
  }

  deletePost(postId: number) {
    return this.httpClient.delete('http://localhost:8080/app/posts/delete/' + postId);
  }
  deleteComment(postId: number, commentId: number): Observable<any> {
    return this.httpClient.delete('http://localhost:8080/app/posts/deletecomment/' + postId + '/' + commentId)
  }
  getComments(postId: number): Observable<Array<CommentModel>> {
    return this.httpClient.get<Array<CommentModel>>('http://localhost:8080/app/posts/comments/' + postId);
  }
}
