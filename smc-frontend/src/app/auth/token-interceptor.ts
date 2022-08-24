import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent, HttpErrorResponse, HttpEventType } from '@angular/common/http';
import { Observable, throwError, BehaviorSubject, catchError, switchMap, filter, take, retry } from 'rxjs';
import { AuthService } from '../services/auth.service';
import { LocalStorageService } from 'ngx-webstorage';
import { NonNullableFormBuilder } from '@angular/forms';



@Injectable({
    providedIn: 'root'
})
export class TokenInterceptor implements HttpInterceptor {

    // isTokenRefreshing = false;
    // refreshTokenSubject: BehaviorSubject<any> = new BehaviorSubject(null);
    newToken: string;
    newReq: HttpRequest<any> = new HttpRequest("GET", 'http://localhost:8080/app/home')
    constructor(public authService: AuthService, private localStorage: LocalStorageService) { }

    intercept(req: HttpRequest<any>, next: HttpHandler) {
        // Get the auth token from the service.
        if (req.url.endsWith('login') || req.url.endsWith('signup')){
            return next.handle(req);
        }
        const authToken = this.authService.getJwtToken();
        const refreshToken = this.authService.getRefreshToken();
    
        // Clone the request and replace the original headers with
        // cloned headers, updated with the authorization.
        const authReq = this.addToken(req, authToken, refreshToken);
    
        // send cloned request with header to the next handler.
        return next.handle(authReq).pipe(
            catchError((err: HttpErrorResponse) => {
                if (err.error.message.startsWith('The Token')) {
                    console.log(err.message)
                    return this.authService.refreshTheToken().pipe(
                        switchMap((response: any) => {
                            if (response.status !== 200) {
                                this.authService.navigateToLogin();
                                throw new Error('ee')
                            }
                            else {
                                this.localStorage.clear('Authentication')
                                this.localStorage.store('Authentication', response.headers.get('Authorization'))
                                return next.handle(this.addToken(authReq, this.authService.getJwtToken(), this.authService.getRefreshToken()))
                            }
                            
                        })
                    )
                }
                return new Observable<HttpEvent<any>>
            })
            
        );
        
    }

    helper() {
        console.log('aaaaaaaaaaaaaaaaaaa')
        this.authService.refreshTheToken().subscribe({
            next: (resp) => {
                console.log('helper here')
            }
        })
    }

    // private handleAuthErrors(req: HttpRequest<any>, next: HttpHandler)
    // : Observable<HttpEvent<any>> {
    // if (!this.isTokenRefreshing) {
    //     this.isTokenRefreshing = true;
    //     this.refreshTokenSubject.next(null);

    //     return this.authService.refreshTheToken().pipe(
    //         switchMap((refreshTokenResponse: any) => {
    //             this.isTokenRefreshing = false;
    //             this.refreshTokenSubject
    //                 .next(refreshTokenResponse.headers.get('Authorization'));
    //             return next.handle(this.addToken(req,
    //                 refreshTokenResponse.headers.get('Authorization'), this.authService.getRefreshToken()));
    //         }),
    //         catchError((err: any) => {
    //             console.log(err);
    //             return next.handle(req);
    //         })
    //     )
    // } else {
    //     return this.refreshTokenSubject.pipe(
    //         filter(result => result !== null),
    //         take(1),
    //         switchMap((res) => {
    //             return next.handle(this.addToken(req,
    //                 this.authService.getJwtToken(), this.authService.getRefreshToken()))
    //         })
    //     );
    //     }
    // }

    addToken(req: HttpRequest<any>, jwt: any, refresh: any) {
        return req.clone({
            headers: req.headers.set('Authorization', jwt).set('Refresh', refresh)
        });
    }
}