export interface MessagePayload {
    body: {
        text: string
        image?: {
            id: number
        }
        video?: {
            id: number
        }
    };
}