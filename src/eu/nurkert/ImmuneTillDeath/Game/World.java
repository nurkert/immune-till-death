package eu.nurkert.ImmuneTillDeath.Game;

import java.util.HashMap;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import eu.nurkert.ImmuneTillDeath.Engine.Back.GContent;
import eu.nurkert.ImmuneTillDeath.Engine.Back.GEntity;
import eu.nurkert.ImmuneTillDeath.Engine.Back.GLocation;
import eu.nurkert.ImmuneTillDeath.Engine.Back.GObject;
import eu.nurkert.ImmuneTillDeath.Engine.Back.Events.GEventHandler.GEventListener;
import eu.nurkert.ImmuneTillDeath.Engine.Back.Events.GEventHandler.GEventMethod;
import eu.nurkert.ImmuneTillDeath.Engine.Back.Events.Essential.HandleObjectsGEvent;
import eu.nurkert.ImmuneTillDeath.Game.Entitys.Cell;
import eu.nurkert.ImmuneTillDeath.Game.Entitys.EntityType;
import eu.nurkert.ImmuneTillDeath.Game.Entitys.Player;

public class World extends GContent implements GEventListener {

	Random random;
	Player player;
	Cell[] cells;
	HashMap<EntityType, Integer> amount;

	public World() {
		random = new Random();
		cells = new Cell[10];
		amount = new HashMap<EntityType, Integer>();
		init();
	}

	private void init() {
		for (int i = 0; i < cells.length; i++) {
			cells[i] = new Cell(
					new GLocation(new Random().nextInt(1000) - 500, new Random().nextInt(1000) - 500, this));
			place(cells[i]);
		}
		new Timer().schedule(new TimerTask() {

			@Override
			public void run() {
				if (player != null && !isFreezed())
					for (int i = 0; i < cells.length; i++) {

						if (cells[i].getLocation().distance(player.getLocation()) > 512) {
							GLocation loc = player.getLocation().copy();
							while (player.getLocation().distance(loc) > 512
									|| player.getLocation().distance(loc) < 256) {
								loc.setAngle(player.getLocation().getInvertAngle());
								loc.addAnlge((float) (new Random().nextDouble()  * Math.PI /2 - Math.PI / 4));
								double x = player.getLocation().getX() + new Random().nextInt(1024) - 512 - 16;
								double y = player.getLocation().getY() + new Random().nextInt(1024) - 512 + 16;
								loc = new GLocation(x, y, loc.getWorld());
							}

							cells[i].teleport(loc);
							if (new Random().nextBoolean())
								cells[i].toggleInfected();
						}
					}

//				for (EntityType type : EntityType.values())
//					System.out.println(type + ": " + getAmount(type));

			}
		}, 0, 10000);
	}

	public Player getPlayer() {
		return this.player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	@GEventMethod()
	public void on(HandleObjectsGEvent event) {
		long currentTime = System.currentTimeMillis();
		if (player != null)
			for (int i= 0; i < getObjects().size(); i++) {
				GObject object = getObjects().get(i);
				if (object.getLocation().distance(player.getLocation()) > 750)
					object.setUseless(true);
			}
	}

	public void takeAmount(EntityType type) {

		if (amount.containsKey(type))
			amount.put(type, amount.get(type) > 0 ? amount.get(type) - 1 : 0);

	}

	public int getAmount(EntityType type) {
		if (amount.containsKey(type))
			return amount.get(type);
		return 0;
	}

	@Override
	public void place(GObject object) {
		if (object instanceof GEntity) {
			GEntity entity = (GEntity) object;
			if (!amount.containsKey(entity.getType()))
				amount.put(entity.getType(), 1);
			else
				amount.put(entity.getType(), amount.get(entity.getType()) + 1);
		}
		super.place(object);
	}

}
