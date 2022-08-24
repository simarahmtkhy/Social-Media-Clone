import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { SignupComponent } from './auth/signup/signup.component';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { LoginComponent } from './auth/login/login.component';
import { NgxWebstorageModule } from 'ngx-webstorage';
import { HomeComponent } from './home/home.component';
import { TokenInterceptor } from './auth/token-interceptor';
import { CreatePostComponent } from './home/create-post/create-post.component';
import { PostComponent } from './home/post/post.component';
import { ProfileComponent } from './profile/profile.component';
import { MyprofileComponent } from './myprofile/myprofile.component';
import { ChatComponent } from './chat/chat.component';
import { CommentComponent } from './home/post/comment/comment.component';
import { ReqComponent } from './myprofile/req/req.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    SignupComponent,
    LoginComponent,
    HomeComponent,
    CreatePostComponent,
    PostComponent,
    ProfileComponent,
    MyprofileComponent,
    ChatComponent,
    CommentComponent,
    ReqComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,
    NgxWebstorageModule.forRoot(),
  ],
  providers: [
    {
    provide: HTTP_INTERCEPTORS,
    useClass: TokenInterceptor,
    multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
