import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { SignupPayload } from './signupPayload';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {

  signupForm: FormGroup;
  signupPayload: SignupPayload;
  constructor(private authService: AuthService, private router: Router) { 
    this.signupPayload = {
      username: '',
      email: '',
      password: ''
    };
  }

  ngOnInit(): void {
    this.signupForm = new FormGroup({
      username: new FormControl('', Validators.required),
      email: new FormControl('', [Validators.email, Validators.required]),
      password: new FormControl('', Validators.required)
    });
  }

  signup() {
    this.signupPayload.username = this.signupForm.get('username')?.value;
    this.signupPayload.email = this.signupForm.get('email')?.value;
    this.signupPayload.password = this.signupForm.get('password')?.value;
    console.log(this.signupPayload.password);

    this.authService.signUp(this.signupPayload).subscribe(data => console.log(data));
    this.router.navigateByUrl('/login')
  }

}
