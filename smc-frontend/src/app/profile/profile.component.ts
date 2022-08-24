import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { throttleTime } from 'rxjs';
import { PostModel } from '../models/postModel';
import { RequestModel } from '../models/requestModel';
import { ProfileService } from '../services/profile.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  username: string = 'hi';
  posts: PostModel[];
  pageNumber: number;
  isFollower: boolean = false;
  sentRequest: boolean;
  requestModel: RequestModel;
  constructor(private route: ActivatedRoute, private service: ProfileService, private router: Router) {
    if (this.route.snapshot.paramMap.get('username') !== null) {
      this.username = this.route.snapshot.paramMap.get('username')!;
    }
    this.pageNumber = 0;
    service.isFollower(this.username).subscribe({
      next: (data) => {
      this.isFollower = data;
      if (this.isFollower) {
        this.sentRequest = false;
        service.getProfile(this.username).subscribe({
          next: (data) => {
            this.posts = data;
          },
          error: (error) => {
            console.log(error)
          }
        })
      }
      else {
        this.service.hasPendingReq(this.username).subscribe({
          next: (data) => {
            if (data) {
              this.sentRequest = true;
            }
            else{
              alert('Follow The User To Access The Profile')
            }
          }
        })
        this.posts = [];
      }
    }
    })
    

  }

  ngOnInit(): void {

  }

  goToPreviousPage() {
    if (this.pageNumber !== 0) {
      this.pageNumber--;
      this.service.goToPage(this.username, this.pageNumber).subscribe({
      next: (data) => {
        this.posts = data;
      }
    })  
    }
    
  }

  goToNextPage() {
    if (!this.noPosts()) {
      this.pageNumber++;
      this.service.goToPage(this.username, this.pageNumber).subscribe({
      next: (data) => {
        this.posts = data;
      }
    })
    }
    
  }

  follow() {
    this.service.follow(this.username).subscribe({
      next: (data) => {
        console.log(data);
        this.sentRequest = true;
        this.requestModel = data;
      }
    });
  }
  unfollow() {
    this.service.unfollow(this.username).subscribe({
      next: () => {
        this.isFollower = !this.isFollower
        window.location.reload();
      }
    });
  }
  cancelReq() {
    console.log(this.requestModel.id);
    this.service.cancelRequest(this.requestModel.id).subscribe();
    this.sentRequest = false;
  }

  noPosts(): boolean {
    return this.posts.length === 0 && this.isFollower;
  }

  getMessages() {
    this.router.navigateByUrl('/users/' + this.username + '/chat')
  }
}
