import { CommentModel } from "./commentModel";
import { ContentBody } from "./contentBody";
import { UserModel } from "./userModel";


export interface PostModel{

    id: number;
    username: string;
    date: string;
    comments: CommentModel[];
    likes: UserModel[];
    body: ContentBody;

}