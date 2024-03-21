package net.icecheese.leagueofminecraft;

import net.icecheese.leagueofminecraft.characteritem.gangplank.GanplankGunItem;
import net.icecheese.leagueofminecraft.characterskill.leesin.entity.SkillEntity;
import net.icecheese.leagueofminecraft.characterskill.leesin.entity.Skill2Entity;
import net.icecheese.leagueofminecraft.characterskill.leesin.entity.SkillEntityRender;
import net.icecheese.leagueofminecraft.characterskill.leesin.item.Skill1_Item;
import net.icecheese.leagueofminecraft.characterskill.leesin.item.Skill2_Item;
import net.icecheese.leagueofminecraft.characterskill.leesin.item.Skill3_Item;
import net.icecheese.leagueofminecraft.characterskill.leesin.item.Skill4_Item;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;


public class MyRegisterObjects {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, LeagueOfMinecraft.MODID);
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, LeagueOfMinecraft.MODID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, LeagueOfMinecraft.MODID);
    public static final DeferredRegister<EntityType<?>> THROWN = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, LeagueOfMinecraft.MODID);

    /*
     * Items register objects
     */
    private static final Item.Properties unstackableItem = new Item.Properties().stacksTo(1).rarity(Rarity.EPIC);
    public static final RegistryObject<Item> SKILL1_ITEM = ITEMS.register("skill1_item", () -> new Skill1_Item(new Item.Properties()));
    public static final RegistryObject<Item> SKILL2_ITEM = ITEMS.register("skill2_item", () -> new Skill2_Item(new Item.Properties()));
    public static final RegistryObject<Item> SKILL3_ITEM = ITEMS.register("skill3_item", () -> new Skill3_Item(new Item.Properties()));
    public static final RegistryObject<Item> SKILL4_ITEM = ITEMS.register("skill4_item", () -> new Skill4_Item(new Item.Properties()));
    public static final RegistryObject<Item> GP_GUN_ITEM = ITEMS.register("gp_gun_item", () -> new GanplankGunItem(unstackableItem));

    /*
     * Throwable projectile register objects
     */
    public static final RegistryObject<EntityType<SkillEntity>> SkillEntity = register("skill_entity", () ->
            EntityType.Builder.<SkillEntity>of(SkillEntity::new, MobCategory.MISC)
                    .sized(0.35F, 0.35F)
                    .clientTrackingRange(4)
                    .updateInterval(10));
    public static final RegistryObject<EntityType<Skill2Entity>> Skill2Entity = register("skill2_entity", () ->
            EntityType.Builder.<Skill2Entity>of(Skill2Entity::new, MobCategory.MISC)
                    .sized(0.35F, 0.35F)
                    .clientTrackingRange(4)
                    .updateInterval(10));

    /*
     * SoundEvent register objects
     */
    public static final RegistryObject<SoundEvent> Q_FIRES = SOUND_EVENTS.register("q_fires", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(LeagueOfMinecraft.MODID, "q_fires")));
    public static final RegistryObject<SoundEvent> Q_HIT = SOUND_EVENTS.register("q_hit", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(LeagueOfMinecraft.MODID, "q_hit")));
    public static final RegistryObject<SoundEvent> Q_2_CAST = SOUND_EVENTS.register("q_2_cast", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(LeagueOfMinecraft.MODID, "q_2_cast")));
    public static final RegistryObject<SoundEvent> W_CAST = SOUND_EVENTS.register("w_cast", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(LeagueOfMinecraft.MODID, "w_cast")));
    public static final RegistryObject<SoundEvent> W_2_CAST = SOUND_EVENTS.register("w_2_cast", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(LeagueOfMinecraft.MODID, "w_2_cast")));
    public static final RegistryObject<SoundEvent> E_CAST = SOUND_EVENTS.register("e_cast", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(LeagueOfMinecraft.MODID, "e_cast")));
    public static final RegistryObject<SoundEvent> E_2_CAST = SOUND_EVENTS.register("e_2_cast", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(LeagueOfMinecraft.MODID, "e_2_cast")));
    public static final RegistryObject<SoundEvent> R_CAST = SOUND_EVENTS.register("r_cast", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(LeagueOfMinecraft.MODID, "r_cast")));

    /*
     * Creative Tab register objects
     */
    public static final RegistryObject<CreativeModeTab> EXAMPLE_TAB = CREATIVE_MODE_TABS.register("example_tab", () -> CreativeModeTab.builder()
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> SKILL1_ITEM.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(SKILL1_ITEM.get());
                output.accept(SKILL2_ITEM.get());
                output.accept(SKILL3_ITEM.get());
                output.accept(SKILL4_ITEM.get());
            }).build());

    private static <T extends Entity> RegistryObject<EntityType<T>> register(String name, Supplier<EntityType.Builder<T>> builder) {
        return THROWN.register(name, () -> builder.get().build(new ResourceLocation(LeagueOfMinecraft.MODID, name).toString()));
    }

}
