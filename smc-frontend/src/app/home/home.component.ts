import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { PostModel } from '../models/postModel';
import { UserModel } from '../models/userModel';
import { AuthService } from '../services/auth.service';
import { HomeService } from '../services/home.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {


  pageNumber: number;
  posts: PostModel[];
  isError: boolean= false;
  searchForm: FormGroup
  searchedUsers: UserModel[];
  constructor(private service: HomeService, private router: Router, private authService: AuthService) {
    this.pageNumber = 0;
    this.searchForm = new FormGroup({
      'text': new FormControl('')
    }
    );
    this.service.getMainFeed().subscribe({next: (data) => {
      this.posts = data;
    }, 
  error: () => {
    this.isError = true;
    router.navigateByUrl('/login')
  }})
   }

  ngOnInit(): void {

  }

  goToNextPage() {
    if (!this.noPosts()) {
      this.pageNumber++;
      this.service.goToPage(this.pageNumber).subscribe({next: (data) => {
      this.posts = data;
    }});
    }
    
  }
  goToPreviousPage() {
    if (this.pageNumber !== 0) {
      this.pageNumber--;
      this.service.goToPage(this.pageNumber).subscribe({next: (data) => {
      this.posts = data;
    }});
    }
    
  }

  goToCreatePost() {
    this.router.navigateByUrl('/createpost');
  }

  logout() {
    this.authService.logout();
  }
  goToProfile() {
    this.router.navigateByUrl('profile');
  }

  noPosts(): boolean {
    return this.posts.length === 0
  }

  search() {
    this.service.search(this.searchForm.get('text')?.value).subscribe({
      next: (data) => {
        this.searchedUsers = data
      }
    })
  }
}
