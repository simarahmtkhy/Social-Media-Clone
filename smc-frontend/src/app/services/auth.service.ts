import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { LocalStorageService } from 'ngx-webstorage';
import { SignupPayload } from '../auth/signup/signupPayload';
import { LoginPayload } from '../auth/login/loginPayload';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
 
  constructor(private httpClient: HttpClient, private localStorage: LocalStorageService, private router: Router) { }

  signUp(signupPayload: SignupPayload): Observable<any>{
    return this.httpClient.post('http://localhost:8080/signup', signupPayload, {responseType : 'text'});
  }

  login(loginPayload: LoginPayload): Observable<any>{
    let body = new HttpParams({
      fromObject : {
        'username' : loginPayload.username,
        'password' : loginPayload.password
      }
    })
    return this.httpClient.post('http://localhost:8080/login', body, {observe: 'response', responseType: 'text'})
  }

  logout() {
    this.localStorage.clear();
    this.router.navigateByUrl('/login')
  }
  getJwtToken() {
    // console.log(this.localStorage.retrieve('Authentication'));
    return this.localStorage.retrieve('Authentication');
  }

  getRefreshToken() {
    return this.localStorage.retrieve('Refresh');
  }

  navigateToLogin() {
    this.router.navigateByUrl('/login')
  }

  refreshTheToken(): Observable<any> {
    console.log('inside service')
    return this.httpClient.post('http://localhost:8080/refresh', null, {observe: 'response', responseType: 'text'})
  }

  navigate(url: string) {
    this.router.navigateByUrl(url);
  }
}
