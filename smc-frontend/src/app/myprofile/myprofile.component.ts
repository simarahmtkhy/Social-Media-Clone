import { Component, OnInit } from '@angular/core';
import { PostModel } from '../models/postModel';
import { RequestModel } from '../models/requestModel';
import { ProfileService } from '../services/profile.service';

@Component({
  selector: 'app-myprofile',
  templateUrl: './myprofile.component.html',
  styleUrls: ['./myprofile.component.css']
})
export class MyprofileComponent implements OnInit {

  posts: PostModel[];
  pageNumber: number;

  showRequestsField: boolean = false;
  constructor(private service: ProfileService) {
    service.getMyProfile().subscribe({
      next: (data) => {
        this.posts = data
      }
    })
    this.pageNumber = 0;
  }

  ngOnInit(): void {
  }

  noPosts(): boolean {
    return this.posts.length === 0;
  }

  goToPreviousPage() {
    if (this.pageNumber !== 0) {
      this.pageNumber--;
      this.service.goToMyPage(this.pageNumber).subscribe({
        next: (data) => {
          this.posts = data;
        }
      }
      )
    }
    
  }

  goToNextPage() {
    if (!this.noPosts()) {
      this.pageNumber++;
      this.service.goToMyPage(this.pageNumber).subscribe({
        next: (data) => {
          this.posts = data;
        }
      });
    }
  }

  showRequests() {
    this.showRequestsField = !this.showRequestsField;
  }


}
