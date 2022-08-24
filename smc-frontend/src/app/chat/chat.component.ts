import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { interval, map, mergeMap, Subscription, switchMap, timer } from 'rxjs';
import { MessageModel } from '../models/messageModel';
import { MessageService } from '../services/message.service';
import { MessagePayload } from './messagePayload';

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css']
})
export class ChatComponent implements OnInit {

  timerSub: Subscription;
  pageNumber: number;
  username: string;
  messages: MessageModel[];
  messageForm: FormGroup;
  messagePayload: MessagePayload;
  constructor(private messageService: MessageService, private route: ActivatedRoute) { 
    this.pageNumber = 0;
    this.messagePayload = {
      body: {
        text: ''
      }
    }
    this.messageForm = new FormGroup({
      'text': new FormControl('')
    })
    if (this.route.snapshot.paramMap.get('username') !== null) {
      this.username = this.route.snapshot.paramMap.get('username')!;
    }
    this.messageService.getMessages(this.username).subscribe({
      next: (data) => {
        this.messages = data;
        console.log(data)
      }
    })
    
    
  }

  ngOnInit(): void {
    interval(1000).pipe(
      mergeMap(() => this.messageService.goToPage(this.username, this.pageNumber)) 
    ).subscribe({
      next: (data) => {
        this.messages = data
      }
    })
  }

  noMessages() {
    return this.messages.length === 0;
  }

  goToPreviousPage() {
    if (this.pageNumber !== 0) {
      this.pageNumber--;
      this.messageService.goToPage(this.username, this.pageNumber).subscribe({
        next: (data) => {
          this.messages = data;
        }
      }
      )
    }
    
  }

  goToNextPage() {
    if (!this.noMessages()) {
      this.pageNumber++;
      this.messageService.goToPage(this.username, this.pageNumber).subscribe({
        next: (data) => {
          this.messages = data;
        }
      });
    }
  }

  postMessage() {
    this.messagePayload.body.text = this.messageForm.get('text')?.value;
    this.messageService.sendMessage(this.username, this.messagePayload).subscribe({
      next: () => {
        window.location.reload();
      }
    })

  }

}
