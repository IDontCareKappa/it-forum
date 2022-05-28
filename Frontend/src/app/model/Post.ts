export class Post {
    id!: number
    author!: string
    title!: string
    content!: string
    createdAt!: string
    updatedAt!: string
    thumbUpCount!: number
    thumbDownCount!: number
    rate!: number
    comments!: Comment[]
    tag!: string

    // constructor(
    //     id: number,
    //     author: string,
    //     title: string,
    //     content: string,
    //     createdAt: string,
    //     updatedAt: string,
    //     thumbUpCount: number,
    //     thumbDownCount: number,
    //     rate: number
    // ) {
    //     this.id = id;
    //     this.author = author;
    //     this.title = title;
    //     this.content = content;
    //     this.createdAt = createdAt;
    //     this.updatedAt = updatedAt;
    //     this.thumbUpCount = thumbUpCount;
    //     this.thumbDownCount = thumbDownCount;
    //     this.rate = rate;
    // }
}