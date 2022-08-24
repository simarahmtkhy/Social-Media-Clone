import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { MessagePayload } from '../chat/messagePayload';
import { MessageModel } from '../models/messageModel';

@Injectable({
  providedIn: 'root'
})
export class MessageService {

  constructor(private httpCLient: HttpClient) { }


  getMessages(username: string): Observable<Array<MessageModel>> {
    return this.httpCLient.get<Array<MessageModel>>('http://localhost:8080/app/messages/' + username);
  }

  goToPage(username: string, pageNumber: number) {
    return this.httpCLient.get<Array<MessageModel>>('http://localhost:8080/app/messages/' + username + '?pageNumber=' + pageNumber)
  }

  sendMessage(username: string, message: MessagePayload) {
    return this.httpCLient.post('http://localhost:8080/app/messages/send_message/' + username, message)
  }
}
