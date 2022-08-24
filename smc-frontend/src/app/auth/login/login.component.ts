import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { Router } from '@angular/router';
import { LocalStorageService } from 'ngx-webstorage';
import { AuthService } from 'src/app/services/auth.service';

import { LoginPayload } from './loginPayload';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;
  loginPayload: LoginPayload;
  isError: boolean = false;
  isLogged: boolean = false;

  constructor(private authService: AuthService, private localStorage: LocalStorageService, private router: Router, private httpClient: HttpClient) { 
    this.loginPayload = {
      username: '',
      password: ''
    };
  }

  ngOnInit(): void {
    this.loginForm = new FormGroup({
      username: new FormControl(''),
      password: new FormControl('')
    })
    // if (this.localStorage.retrieve('Authentication') !== null) {

    //   this.router.navigateByUrl('home')
    // }
  }

  login() {
    this.loginPayload.username = this.loginForm.get('username')?.value;
    this.loginPayload.password = this.loginForm.get('password')?.value;

    this.authService.login(this.loginPayload).subscribe({next: (data) => {
      this.localStorage.store('Authentication', data.headers.get('Authorization'))
      this.localStorage.store('Refresh', data.headers.get('Refresh'))
      console.log('success')
      this.isLogged = true;
      this.router.navigateByUrl('/home')
    }
      , 
    error: () => {this.isError = true; console.log('error')}});

  }
}
