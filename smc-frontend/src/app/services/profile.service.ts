import { HashLocationStrategy } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, retry } from 'rxjs';
import { PostModel } from '../models/postModel';
import { RequestModel } from '../models/requestModel';

@Injectable({
  providedIn: 'root'
})
export class ProfileService {

  constructor(private httpClient: HttpClient) { 

  }

  getProfile(username: string): Observable<Array<PostModel>> {
    console.log(username)
    return this.httpClient.get<Array<PostModel>>('http://localhost:8080/app/users/' + username);
  }
  getMyProfile(): Observable<Array<PostModel>> {
    return this.httpClient.get<Array<PostModel>>('http://localhost:8080/app/profile');
  }
  goToMyPage(pageNumber: number): Observable<Array<PostModel>> {
    return this.httpClient.get<Array<PostModel>>('http://localhost:8080/app/profile?pageNumber=' + pageNumber)
  }
  goToPage(username: string, pageNumber: number): Observable<Array<PostModel>> {
    return this.httpClient.get<Array<PostModel>>('http://localhost:8080/app/users/' + username + '?pageNumber=' + pageNumber)
  }
  follow(username: string ): Observable<RequestModel> {
    return this.httpClient.post<RequestModel>('http://localhost:8080/app/follow/' + username, null);
  }
  unfollow(username: string ) {
    return this.httpClient.delete('http://localhost:8080/app/unfollow/' + username);
  }
  isFollower(username: string): Observable<boolean>{
    return this.httpClient.get<boolean>('http://localhost:8080/app/isfollower/' + username);
  }
  getFollowRequests(): Observable<Array<RequestModel>> {
    return this.httpClient.get<Array<RequestModel>>('http://localhost:8080/app/requests');
  }
  acceptRequest(reqId: number) {
    return this.httpClient.post('http://localhost:8080/app/accept/' + reqId, null);
  }
  rejectRequest(reqId: number) {
    return this.httpClient.delete('http://localhost:8080/app/reject/' + reqId);
  }
  cancelRequest(reqId: number) {
    return this.httpClient.delete('http://localhost:8080/app/cancel/' + reqId);
  }
  hasPendingReq(username: string) {
    return this.httpClient.get('http://localhost:8080/app/haspendingreq/' + username);
  }
}
