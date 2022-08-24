import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { PostModel } from '../models/postModel';
import { UserModel } from '../models/userModel';

@Injectable({
  providedIn: 'root'
})
export class HomeService {

  constructor(private httpClient: HttpClient) { }

  getMainFeed(): Observable<Array<PostModel>> {
    return this.httpClient.get<Array<PostModel>>('http://localhost:8080/app/home')
  }
  goToPage(pageNumber: number): Observable<Array<PostModel>> {
    if (pageNumber >= 0) {
      return this.httpClient.get<Array<PostModel>>('http://localhost:8080/app/home?pageNumber=' + pageNumber)
    }
    throw new Error('error')
  }

  search(search: string): Observable<Array<UserModel>> {
    return this.httpClient.get<Array<UserModel>>('http://localhost:8080/app/search/' + search);
  }
}
