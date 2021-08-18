/*
 * This file is part of Applied Energistics 2.
 * Copyright (c) 2013 - 2014, AlgorithmX2, All rights reserved.
 *
 * Applied Energistics 2 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Applied Energistics 2 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Applied Energistics 2.  If not, see <http://www.gnu.org/licenses/lgpl>.
 */


package idealindustrial.util.item;


import appeng.util.Platform;
import appeng.util.item.AESharedNBT;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.GameRegistry.UniqueIdentifier;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;
import java.util.Set;

/**
 * modified version of AEItemDef for GT proposes
 */
public class ItemDef
{

	static final AESharedNBT LOW_TAG = new AESharedNBT( Integer.MIN_VALUE );
	static final AESharedNBT HIGH_TAG = new AESharedNBT( Integer.MAX_VALUE );

	private final int itemID;
	private final Item item;
	private int myHash;
	private int def;
	private int damageValue;
	private int displayDamage;
	private int maxDamage;
	private AESharedNBT tagCompound;
	private Set<String> ores;
	@SideOnly( Side.CLIENT )
	private String displayName;
	@SideOnly( Side.CLIENT )
	private List tooltip;
	@SideOnly( Side.CLIENT )
	private UniqueIdentifier uniqueID;

	public ItemDef(final Item it )
	{
		this.item = it;
		this.itemID = Item.getIdFromItem( it );
	}

	ItemDef copy()
	{
		final ItemDef t = new ItemDef( this.getItem() );
		t.def = this.def;
		t.setDamageValue( this.getDamageValue() );
		t.setDisplayDamage( this.getDisplayDamage() );
		t.setMaxDamage( this.getMaxDamage() );
		t.setTagCompound( this.getTagCompound() );
		return t;
	}

	@Override
	public boolean equals( final Object obj )
	{
		if( obj == null )
		{
			return false;
		}
		if( this.getClass() != obj.getClass() )
		{
			return false;
		}
		final ItemDef other = (ItemDef) obj;
		return other.getDamageValue() == this.getDamageValue() && other.getItem() == this.getItem() && this.getTagCompound() == other.getTagCompound();
	}

	boolean isItem( final ItemStack otherStack )
	{
		// hackery!
		final int dmg = this.getDamageValueHack( otherStack );

		if( this.getItem() == otherStack.getItem() && dmg == this.getDamageValue() )
		{
			if( ( this.getTagCompound() != null ) == otherStack.hasTagCompound() )
			{
				return true;
			}

			if( this.getTagCompound() != null && otherStack.hasTagCompound() )
			{
				return Platform.NBTEqualityTest( this.getTagCompound(), otherStack.getTagCompound() );
			}

			return true;
		}
		return false;
	}


	int getDamageValueHack( final ItemStack is )
	{
		return Items.blaze_rod.getDamage( is );
	}

	void reHash()
	{
		this.def = this.getItemID() << Platform.DEF_OFFSET | this.getDamageValue();
		this.myHash = this.def ^ ( this.getTagCompound() == null ? 0 : System.identityHashCode( this.getTagCompound() ) );
	}

	AESharedNBT getTagCompound()
	{
		return this.tagCompound;
	}

	void setTagCompound( final AESharedNBT tagCompound )
	{
		this.tagCompound = tagCompound;
	}

	int getDamageValue()
	{
		return this.damageValue;
	}

	int setDamageValue( final int damageValue )
	{
		this.damageValue = damageValue;
		return damageValue;
	}

	Item getItem()
	{
		return this.item;
	}

	int getDisplayDamage()
	{
		return this.displayDamage;
	}

	void setDisplayDamage( final int displayDamage )
	{
		this.displayDamage = displayDamage;
	}

	String getDisplayName()
	{
		return this.displayName;
	}

	void setDisplayName( final String displayName )
	{
		this.displayName = displayName;
	}

	List getTooltip()
	{
		return this.tooltip;
	}

	List setTooltip( final List tooltip )
	{
		this.tooltip = tooltip;
		return tooltip;
	}

	UniqueIdentifier getUniqueID()
	{
		if (uniqueID == null) {
			uniqueID = GameRegistry.findUniqueIdentifierFor(item);
		}
		return this.uniqueID;
	}

	UniqueIdentifier setUniqueID( final UniqueIdentifier uniqueID )
	{
		this.uniqueID = uniqueID;
		return uniqueID;
	}

	int getItemID()
	{
		return this.itemID;
	}

	int getMaxDamage()
	{
		return this.maxDamage;
	}

	void setMaxDamage( final int maxDamage )
	{
		this.maxDamage = maxDamage;
	}

	/**
	 * TODO: Check if replaceable by hashCode();
	 */
	int getMyHash()
	{
		return this.myHash;
	}

	boolean hasOre(String ore) {
		return ores.contains(ore);
	}



}
