// Automatically Generated -- DO NOT EDIT
// lawscraper.shared.LawRequestFactory
package lawscraper.shared;
import java.util.Arrays;
import com.google.web.bindery.requestfactory.vm.impl.OperationData;
import com.google.web.bindery.requestfactory.vm.impl.OperationKey;
public final class LawRequestFactoryDeobfuscatorBuilder extends com.google.web.bindery.requestfactory.vm.impl.Deobfuscator.Builder {
{
withOperation(new OperationKey("F3jT2FwOnhpUMUmcmC0PWZ8SAA8="),
  new OperationData.Builder()
  .withClientMethodDescriptor("()Lcom/google/web/bindery/requestfactory/shared/Request;")
  .withDomainMethodDescriptor("()V")
  .withMethodName("scrapeAll")
  .withRequestContext("lawscraper.shared.LawRequestFactory$LawRequest")
  .build());
withOperation(new OperationKey("1OWTC81UJ_gGl8A_DygehV8ceeE="),
  new OperationData.Builder()
  .withClientMethodDescriptor("(Ljava/lang/Long;)Lcom/google/web/bindery/requestfactory/shared/Request;")
  .withDomainMethodDescriptor("(Ljava/lang/Long;)Llawscraper/server/entities/law/Law;")
  .withMethodName("findLaw")
  .withRequestContext("lawscraper.shared.LawRequestFactory$LawRequest")
  .build());
withRawTypeToken("w1Qg$YHpDaNcHrR5HZ$23y518nA=", "com.google.web.bindery.requestfactory.shared.EntityProxy");
withRawTypeToken("FXHD5YU0TiUl3uBaepdkYaowx9k=", "com.google.web.bindery.requestfactory.shared.BaseProxy");
withRawTypeToken("n6zuiBulkNUTpqe2prIndRS4nkM=", "lawscraper.shared.proxies.LawProxy");
withClientToDomainMappings("lawscraper.server.entities.law.Law", Arrays.asList("lawscraper.shared.proxies.LawProxy"));
}}
