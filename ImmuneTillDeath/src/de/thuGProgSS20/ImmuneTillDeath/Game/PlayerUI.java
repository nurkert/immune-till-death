package de.thuGProgSS20.ImmuneTillDeath.Game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.ArrayList;

import de.thuGProgSS20.ImmuneTillDeath.Engine.Back.GContent;
import de.thuGProgSS20.ImmuneTillDeath.Engine.Back.GLocation;
import de.thuGProgSS20.ImmuneTillDeath.Engine.Back.GObject;
import de.thuGProgSS20.ImmuneTillDeath.Engine.Back.GPlayer;
import de.thuGProgSS20.ImmuneTillDeath.Engine.Front.GBarTexture;
import de.thuGProgSS20.ImmuneTillDeath.Engine.Front.GSelectiveTexture;
import de.thuGProgSS20.ImmuneTillDeath.Engine.Front.GTexture;
import de.thuGProgSS20.ImmuneTillDeath.Game.Entitys.Player;

public class PlayerUI extends GObject {

	GPlayer player;
	GBarTexture[] bars;
	double health, power;
	GTexture[] textures;
	GSelectiveTexture digits;

	public PlayerUI(GPlayer player) {
		super(player.getLocation().copy(), GLayor.OVERLAY2, false, true);
		this.player = player;
		bars = new GBarTexture[2];
		bars[0] = new GBarTexture("/textures/ui/health_bar.png");
		bars[1] = new GBarTexture("/textures/ui/power_bar.png");
		health = 10;
		power = 10;

		init();
	}

	private void init() {
		textures = new GTexture[4];
		textures[0] = new GTexture("/textures/ui/loadingscreen.png");
		textures[1] = new GTexture("/textures/ui/paused.png");
		textures[2] = new GTexture("/textures/ui/gameover.png");
		textures[3] = new GTexture("/textures/ui/kills.png");
		digits = new GSelectiveTexture("/textures/ui/digits.png");
	}

	@Override
	public void handle(double diff, GContent world) {
		double health = player.getHealth() / player.getMaxHealth() * 10;
		double power = ((Player) player).getPower();
		
		if(this.health - health < -0.25) {
			this.health += 0.25*diff*50;
		} else if(this.health - health > 0.25) {
			this.health -= 0.25*diff*50;
		}

		if(this.power - power < -0.25) {
			this.power += 0.25*diff*50;
		} else if(this.power - power > 0.25) {
			this.power -= 0.25*diff*50;
		}
		
		teleport(player.getLocation().getWorld().getViewCenter().copy().add(50, 50));
	}

	@Override
	public void draw(BufferedImage image, ImageObserver observer, GLocation viewCenter, double screenFactor) {

		if (player.getLocation().getWorld().isFreezed()) {

			Graphics graphics = image.getGraphics();

			graphics.setColor(new Color(0, 0, 0, 64));
			graphics.fillRect(0, 0, image.getWidth(), image.getHeight());

			switch (player.getLocation().getWorld().getFreezeReason()) {
			case START:
				Image loading = textures[0].getImage();

				int loading_width = (int) (loading.getWidth(observer) * screenFactor);
				int loading_height = (int) (loading.getHeight(observer) * screenFactor);

				image.getGraphics().drawImage(loading, image.getWidth() / 2 - loading_width / 2,
						image.getHeight() / 2 - loading_height / 2, loading_width, loading_height, null);
				break;
			case PAUSED:

				Image paused = textures[1].getImage();

				int paused_width = (int) (paused.getWidth(observer) * screenFactor / 2);
				int paused_height = (int) (paused.getHeight(observer) * screenFactor / 2);

				image.getGraphics().drawImage(paused, 5, 30, paused_width, paused_height, null);

				break;
			case GAMEOVER:
				Image gameover = textures[2].getImage();

				int gameover_width = (int) (gameover.getWidth(observer) * screenFactor);
				int gameover_height = (int) (gameover.getHeight(observer) * screenFactor);

				image.getGraphics().drawImage(gameover, image.getWidth() / 2 - gameover_width / 2,
						image.getHeight() / 2 - gameover_height / 2, gameover_width, gameover_height, null);

				int score = ((Player) player).getScore();

				ArrayList<Integer> stack = new ArrayList<Integer>();
				while (score > 0) {
					stack.add(score % 10);
					score = score / 10;
				}

				Image killed = textures[3].getImage();

				int killed_width = (int) (killed.getWidth(observer) * screenFactor * 0.75);
				int killed_height = (int) (killed.getHeight(observer) * screenFactor * 0.75);

				image.getGraphics().drawImage(killed, image.getWidth() / 3 - killed_width / 2,
						(int) (image.getHeight() / 2.4 + gameover_height / 2 - killed_height / 2), killed_width,
						killed_height, null);
				if (stack.size() == 0) {
					Image digit = digits.getImage(0);

					int digit_width = (int) (digit.getWidth(observer) * screenFactor * 0.75);
					int digit_height = (int) (digit.getHeight(observer) * screenFactor * 0.75);

					image.getGraphics().drawImage(digit,
							image.getWidth() / 3 - killed_width / 2 + 60 + (int) (digit_width / 1.8),
							(int) (image.getHeight() / 2.4 + gameover_height / 2 - killed_height / 2), digit_width,
							digit_height, null);
				} else
					for (int i = 0; i < stack.size(); i++) {
						Image digit = digits.getImage(stack.get(stack.size() - 1 - i));

						int digit_width = (int) (digit.getWidth(observer) * screenFactor * 0.75);
						int digit_height = (int) (digit.getHeight(observer) * screenFactor * 0.75);

						image.getGraphics().drawImage(digit,
								image.getWidth() / 3 - killed_width / 2 + 60 + (int) (digit_width / 1.8) * (i + 1),
								(int) (image.getHeight() / 2.4 + gameover_height / 2 - killed_height / 2), digit_width,
								digit_height, null);
					}
				break;

			default:
				break;
			}

		} else {
			for (int i = 0; i < bars.length; i++) {
				Image _texture = bars[i].getImage(i == 0 ? health : power);

				if (_texture == null)
					return;

				int width = (int) (_texture.getWidth(observer) * screenFactor / 2.5);
				int height = (int) (_texture.getHeight(observer) * screenFactor / 2.5);

				image.getGraphics().drawImage(_texture, 20, 40 + height * i + 5 * i, width, height, null);
			}

			Image killed = textures[3].getImage();

			int killed_width = (int) (killed.getWidth(observer) * screenFactor / 2.5);
			int killed_height = (int) (killed.getHeight(observer) * screenFactor / 2.5);

			image.getGraphics().drawImage(killed, image.getWidth() - killed_width - 20, 40, killed_width, killed_height,
					null);

			int score = ((Player) player).getScore();
//			System.out.println(score);
			ArrayList<Integer> stack = new ArrayList<Integer>();
			while (score > 0) {
				stack.add(score % 10);
				score = score / 10;
//				System.out.println(score);
			}
//			System.out.println("stack size: " + stack.size());
			if (stack.size() > 0)
//				System.out.println("stack contemt: " + stack.get(0));
				for (int i = 0; i < stack.size(); i++) {
					Image digit = digits.getImage(stack.get(i));

					int digit_width = (int) (digit.getWidth(observer) * screenFactor / 2.5);
					int digit_height = (int) (digit.getHeight(observer) * screenFactor / 2.5);

					image.getGraphics().drawImage(digit,
							(int) (image.getWidth() - 20 - killed_width * 1.4 - (digit_width / 2) * (i + 1)), 45,
							digit_width, digit_height, null);
				}
		}

	}

}
