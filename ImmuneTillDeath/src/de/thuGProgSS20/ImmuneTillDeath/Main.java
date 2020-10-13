package de.thuGProgSS20.ImmuneTillDeath;

import java.util.Random;

import de.thuGProgSS20.ImmuneTillDeath.Engine.GFrame;
import de.thuGProgSS20.ImmuneTillDeath.Engine.Back.GLocation;
import de.thuGProgSS20.ImmuneTillDeath.Engine.Back.Events.GEventHandler;
import de.thuGProgSS20.ImmuneTillDeath.Engine.Back.Events.GEventHandler.GEventListener;
import de.thuGProgSS20.ImmuneTillDeath.Engine.Back.Events.GEventHandler.GEventMethod;
import de.thuGProgSS20.ImmuneTillDeath.Engine.Back.Events.Essential.HandleObjectsGEvent;
import de.thuGProgSS20.ImmuneTillDeath.Engine.Front.GTexture;
import de.thuGProgSS20.ImmuneTillDeath.Game.PlayerUI;
import de.thuGProgSS20.ImmuneTillDeath.Game.World;
import de.thuGProgSS20.ImmuneTillDeath.Game.Entitys.Player;
import de.thuGProgSS20.ImmuneTillDeath.Game.Entitys.Mobs.Covid19;
import de.thuGProgSS20.ImmuneTillDeath.Game.Entitys.Mobs.Virus;

public class Main implements GEventListener {

	static World world;
	static Player player;
	public static GFrame frame;

	public static void main(String[] args) {
		GEventHandler.register(new Main());

		Thread mainThread = new Thread("mainThread") {
			public void run() {

				frame = new GFrame("ImmuneTillDeath");
				world = new World();
				GEventHandler.register(world);
				frame.setContent(world);
				frame.setBackground(new GTexture("/textures/background.png"));

				for (int i = 0; i < 50; i++) {
					Virus virus = new Random().nextBoolean()
							? new Covid19(new GLocation(new Random().nextInt(1000) - 500,
									new Random().nextInt(1000) - 500, world), 7)
							: new Virus(new GLocation(new Random().nextInt(1000) - 500,
									new Random().nextInt(1000) - 500, world), 5);

					world.place(virus);
				}
				
//				for (int i = 0; i < 50; i++) {
//
//					world.place(new BloodCellItem(new GLocation(new Random().nextInt(1000) - 500,
//							new Random().nextInt(1000) - 500, world)));
//					world.place(new SyringeItem(new GLocation(new Random().nextInt(1000) - 500,
//							new Random().nextInt(1000) - 500, world)));
//				}
				

				player = new Player(new GLocation(0, 0, world), frame.getPanel().getContent().getViewCenter());
				world.setPlayer(player);
				world.place(player);

				world.place(new PlayerUI(player));

				frame.update(frame.getGraphics());

//				GSound.playSound("/sounds/melody.wav");
			}
		};
		mainThread.start();
		
	}

	@GEventMethod
	public void on(HandleObjectsGEvent event) {

		// System.out.println("pressed key:" + event.getKey());
	}

}
