import { ContentBody } from "./contentBody";

export interface MessageModel {
    id: number;
    senderName: string;
    receiverName: string;
    content: ContentBody;
    dateTime: string;
}