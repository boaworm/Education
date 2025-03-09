class Aquarium {
    constructor(width, height, waterLine, borderWidth) {
        this.width = width;
        this.height = height;
        this.waterLine = waterLine;
        this.borderWidth = borderWidth;
    }

    removeFish(fishId) {
        fishMap.delete(fishId);
    }

    draw(ctx) {
        ctx.fillStyle = '#ADD8E6';
        ctx.fillRect(0, 0, this.width, this.height);

        ctx.fillStyle = 'darkgray';
        ctx.fillRect(0, 0, this.borderWidth, this.height);
        ctx.fillRect(this.width - this.borderWidth, 0, this.borderWidth, this.height);
        ctx.fillRect(0, this.height - this.borderWidth, this.width, this.borderWidth);

        ctx.fillStyle = 'blue';
        ctx.fillRect(0, this.waterLine, this.width, this.height - this.waterLine);

        for (const fish of fishMap.values()) {
            fish.draw(ctx);
        }
    }
}
