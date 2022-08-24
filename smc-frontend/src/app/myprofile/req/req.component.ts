import { Component, OnInit } from '@angular/core';
import { RequestModel } from 'src/app/models/requestModel';
import { ProfileService } from 'src/app/services/profile.service';

@Component({
  selector: 'app-req',
  templateUrl: './req.component.html',
  styleUrls: ['./req.component.css']
})
export class ReqComponent implements OnInit {

  requests: RequestModel[];
  constructor(private service: ProfileService) { 

  }

  ngOnInit(): void {
    this.service.getFollowRequests().subscribe({
      next: (data) => {
        this.requests = data;
      }
    })
  }

  acceptRequest(req: RequestModel) {
    this.service.acceptRequest(req.id).subscribe({
      next: () => {
        this.requests = this.requests.filter((request) => request.id !== req.id)
      }
    });
  }

  rejectRequest(req: RequestModel) {
    this.service.rejectRequest(req.id).subscribe({
      next: () => {
        this.requests = this.requests.filter((request) => request.id !== req.id)
      }
    });
  }
}
