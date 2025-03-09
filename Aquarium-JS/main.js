const canvas = document.getElementById('aquariumCanvas');
const ctx = canvas.getContext('2d');

const WIDTH = 1000;
const HEIGHT = 600;
const WATER_LINE = 100;
const BORDER_WIDTH = 5;

canvas.width = WIDTH;
canvas.height = HEIGHT;

const fishMap = new Map();
let nextFishId = 0;

const aquarium = new Aquarium(WIDTH, HEIGHT, WATER_LINE, BORDER_WIDTH);

fishMap.set(nextFishId, new RedFish(WIDTH / 2, HEIGHT / 2, aquarium, nextFishId++));

for (let i = 0; i < 5; i++) {
    const x = Math.random() * (WIDTH - 100) + 50;
    const y = Math.random() * (HEIGHT - 150) + 100;
    fishMap.set(nextFishId, new OrangeFish(x, y, aquarium, nextFishId++));
}

fishMap.set(nextFishId, new NorthernPike(WIDTH / 4, HEIGHT / 4, aquarium, nextFishId++));

function animate() {
    setTimeout(() => { // Introduce a delay
        ctx.clearRect(0, 0, WIDTH, HEIGHT);
        aquarium.draw(ctx);

        const fishList = Array.from(fishMap.values());
        for (const fish of fishList) {
            fish.move(fishList);
        }

        requestAnimationFrame(animate);
    }, 50); // Delay of 50 milliseconds (adjust as needed)
}

animate();
