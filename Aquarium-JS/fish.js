class Fish {
    constructor(x, y, aquarium, id) {
        this.x = x;
        this.y = y;
        this.aquarium = aquarium;
        this.id = id;
        this.facingRight = Math.random() < 0.5;
        this.changeDirection();
    }

    move(fishList) {
        this.x += this.dx;
        this.y += this.dy;

        if (this.x < this.aquarium.borderWidth) {
            this.x = this.aquarium.borderWidth;
            this.flipFish();
        }
        if (this.x > this.aquarium.width - this.aquarium.borderWidth - this.getWidth()) {
            this.x = this.aquarium.width - this.aquarium.borderWidth - this.getWidth();
            this.flipFish();
        }
        if (this.y < this.aquarium.waterLine) {
            this.y = this.aquarium.waterLine;
            this.flipFish();
        }
        if (this.y > this.aquarium.height - this.aquarium.borderWidth - this.getHeight()) {
            this.y = this.aquarium.height - this.aquarium.borderWidth - this.getHeight();
            this.flipFish();
        }

        for (const otherFish of fishList) {
            if (otherFish !== this && this.collidesWith(otherFish)) {
                if (this instanceof NorthernPike) {
                    this.aquarium.removeFish(otherFish.id);
                } else {
                    this.flipFish();
                }
                break;
            }
        }
    }

    flipFish() {
        this.facingRight = !this.facingRight;
        this.changeDirection();
    }

    changeDirection() {
        const angle = Math.PI * (Math.random() * 121 + 30) / 180;
        this.angle = Math.random() < 0.5 ? -angle : angle;
        this.dx = Math.cos(this.angle) * this.getSpeed();
        this.dy = Math.sin(this.angle) * this.getSpeed();

        if (!this.facingRight) {
            this.dx = -this.dx;
        }
    }

    collidesWith(otherFish) {
        const thisLeft = this.x;
        const thisRight = this.x + this.getWidth();
        const thisTop = this.y;
        const thisBottom = this.y + this.getHeight();

        const otherLeft = otherFish.x;
        const otherRight = otherFish.x + otherFish.getWidth();
        const otherTop = otherFish.y;
        const otherBottom = otherFish.y + otherFish.getHeight();

        return !(thisLeft > otherRight || thisRight < otherLeft || thisTop > otherBottom || thisBottom < otherTop);
    }

    draw(ctx) {
        ctx.fillStyle = this.getFishColor();
        ctx.beginPath();
        ctx.ellipse(this.x, this.y, this.getWidth() / 2, this.getHeight() / 2, 0, 0, 2 * Math.PI);
        ctx.fill();

        ctx.fillStyle = this.getFishColor();
        ctx.beginPath();
        const tailX = this.facingRight ? [this.x - 8, this.x - 8 + this.getTailWidth(), this.x - 8] : [this.x + this.getWidth() + 8, this.x + this.getWidth() + 8 - this.getTailWidth(), this.x + this.getWidth() + 8];
        const tailY = [this.y, this.y + this.getHeight() / 2, this.y + this.getHeight()];
        ctx.moveTo(tailX[0], tailY[0]);
        ctx.lineTo(tailX[1], tailY[1]);
        ctx.lineTo(tailX[2], tailY[2]);
        ctx.fill();

        ctx.fillStyle = 'black';
        const eyeX = this.facingRight ? this.x + this.getWidth() * 0.8 - 2 : this.x + this.getWidth() * 0.2 - 2;
        ctx.beginPath();
        ctx.ellipse(eyeX, this.y, 2, 2, 0, 0, 2 * Math.PI);
        ctx.fill();
    }

    getFishColor() {
        return 'red';
    }

    getTailWidth() {
        return 15;
    }

    getWidth() {
        return 30;
    }

    getHeight() {
        return 20;
    }

    getSpeed() {
        return 5;
    }
}
